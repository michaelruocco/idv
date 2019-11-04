package uk.co.mruoc.idv.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.internal.MongoClientImpl;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;
import uk.co.mruoc.idv.lockout.dao.LockoutPolicyDao;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersConverter;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityDocumentConverter;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityConverterDelegator;
import uk.co.mruoc.idv.mongo.dao.activity.MonetaryAmountDocumentConverter;
import uk.co.mruoc.idv.mongo.dao.activity.OnlinePurchaseDocumentConverter;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelDocumentConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasDocumentConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasesDocumentConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityDocumentConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityRepository;
import uk.co.mruoc.idv.mongo.MongoIndexResolver;
import uk.co.mruoc.idv.mongo.identity.dao.MongoIdentityDao;
import uk.co.mruoc.idv.mongo.lockout.dao.policy.LockoutPolicyDocumentConverter;
import uk.co.mruoc.idv.mongo.lockout.dao.policy.LockoutPolicyDocumentConverterDelegator;
import uk.co.mruoc.idv.mongo.lockout.dao.policy.LockoutPolicyRepository;
import uk.co.mruoc.idv.mongo.lockout.dao.policy.MaxAttemptsLockoutPolicyDocumentConverter;
import uk.co.mruoc.idv.mongo.lockout.dao.policy.MongoLockoutPolicyDao;
import uk.co.mruoc.idv.mongo.lockout.dao.attempt.MongoVerificationAttemptsDao;
import uk.co.mruoc.idv.mongo.lockout.dao.attempt.VerificationAttemptDocumentConverter;
import uk.co.mruoc.idv.mongo.lockout.dao.attempt.VerificationAttemptsDocumentConverter;
import uk.co.mruoc.idv.mongo.lockout.dao.attempt.VerificationAttemptsRepository;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.eligibility.EligibilityDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.MongoVerificationContextDao;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationSequencesConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.CardCredentialsDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.CardNumberDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.CardNumbersDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.MobileNumberDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.MobileNumbersDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.MobilePinsentryDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.OneTimePasscodeSmsDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PasscodeSettingsDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PhysicalPinsentryDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PushNotificationDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationContextDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationContextRepository;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodDocumentConverterDelegator;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodsDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationSequenceDocumentConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsDocumentConverter;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = "uk.co.mruoc.idv.mongo")
@Profile("!stub")
@Slf4j
public class MongoDaoConfig {

    @Bean
    @Profile("in-memory-mongo")
    public MongoClient inMemoryMongo() {
        log.info("starting in memory mongo server");
        final MongoServer server = new MongoServer(new MemoryBackend());
        final InetSocketAddress socketAddress = server.bind();
        final List<ServerAddress> hosts = Collections.singletonList(new ServerAddress(socketAddress));
        final MongoClientSettings clientSettings = MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(hosts))
                .build();
        return MongoClients.create(clientSettings);
    }

    @Bean
    @Profile("!in-memory-mongo")
    public MongoClient mongo() {
        log.info("attempting to connect to running mongo server");
        final MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder();
        loadMongoConnectionString().ifPresent(settingsBuilder::applyConnectionString);
        final MongoDriverInformation driverInformation = MongoDriverInformation.builder().build();
        return new MongoClientImpl(settingsBuilder.build(), driverInformation);
    }

    @Bean
    public MongoMappingContext mappingContext() {
        final MongoMappingContext context = new MongoMappingContext();
        context.setAutoIndexCreation(false);
        return context;
    }

    @Bean
    public MongoDbFactory mongoDbFactory(final MongoClient mongoClient) {
        return new SimpleMongoClientDbFactory(mongoClient, "verification-context");
    }

    @Bean
    public MongoTemplate mongoTemplate(final MongoDbFactory dbFactory,
                                       final MongoMappingContext mappingContext) {
        final DbRefResolver resolver = new DefaultDbRefResolver(dbFactory);
        final MongoConverter converter = new MappingMongoConverter(resolver, mappingContext);
        return new MongoTemplate(dbFactory, converter);
    }

    @Bean
    public MongoIndexResolver indiciesResolver(final MongoTemplate template,
                                               final MongoMappingContext mappingContext) {
        return new MongoIndexResolver(template, mappingContext);
    }

    @Bean
    public IndiciesResolverListener indiciesResolverListener(final MongoIndexResolver resolver) {
        return new IndiciesResolverListener(resolver);
    }

    @Bean
    public ChannelDocumentConverter channelConverter() {
        return new ChannelDocumentConverter();
    }

    @Bean
    public MonetaryAmountDocumentConverter amountConverter() {
        return new MonetaryAmountDocumentConverter();
    }

    @Bean
    public OnlinePurchaseDocumentConverter onlinePurchaseConverter(final MonetaryAmountDocumentConverter amountConverter) {
        return new OnlinePurchaseDocumentConverter(amountConverter);
    }

    @Bean
    public ActivityConverterDelegator activityConverterDelegator(final Collection<ActivityDocumentConverter> activityConverters) {
        return new ActivityConverterDelegator(activityConverters);
    }

    @Bean
    public AliasDocumentConverter aliasConverter(final AliasFactory aliasFactory) {
        return new AliasDocumentConverter(aliasFactory);
    }

    @Bean
    public AliasesDocumentConverter aliasesConverter(final AliasDocumentConverter aliasConverter) {
        return new AliasesDocumentConverter(aliasConverter);
    }

    @Bean
    public IdentityDocumentConverter identityConverter(final AliasesDocumentConverter aliasesConverter) {
        return new IdentityDocumentConverter(aliasesConverter);
    }

    @Bean
    public VerificationResultDocumentConverter resultConverter() {
        return new VerificationResultDocumentConverter();
    }

    @Bean
    public VerificationResultsDocumentConverter resultsConverter(final VerificationResultDocumentConverter resultConverter) {
        return new VerificationResultsDocumentConverter(resultConverter);
    }

    @Bean
    public EligibilityDocumentConverter eligibilityConverter() {
        return new EligibilityDocumentConverter();
    }

    @Bean
    public CardNumberDocumentConverter cardNumberConverter() {
        return new CardNumberDocumentConverter();
    }

    @Bean
    public CardNumbersDocumentConverter cardNumbersConverter(final CardNumberDocumentConverter cardNumberConverter) {
        return new CardNumbersDocumentConverter(cardNumberConverter);
    }

    @Bean
    public MobileNumberDocumentConverter mobileNumberConverter() {
        return new MobileNumberDocumentConverter();
    }

    @Bean
    public MobileNumbersDocumentConverter mobileNumbersConverter(final MobileNumberDocumentConverter mobileNumberConverter) {
        return new MobileNumbersDocumentConverter(mobileNumberConverter);
    }

    @Bean
    public PasscodeSettingsDocumentConverter passcodeSettingsConverter() {
        return new PasscodeSettingsDocumentConverter();
    }

    @Bean
    public VerificationMethodDocumentConverter pushNotificationConverter(final VerificationResultsDocumentConverter resultsConverter,
                                                                         final EligibilityDocumentConverter eligibilityConverter) {
        return PushNotificationDocumentConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .build();
    }

    @Bean
    public VerificationMethodDocumentConverter physicalPinsentryConverter(final VerificationResultsDocumentConverter resultsConverter,
                                                                          final EligibilityDocumentConverter eligibilityConverter,
                                                                          final CardNumbersDocumentConverter cardNumbersConverter) {
        return PhysicalPinsentryDocumentConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .cardNumbersConverter(cardNumbersConverter)
                .build();
    }

    @Bean
    public VerificationMethodDocumentConverter mobilePinsentryConverter(final VerificationResultsDocumentConverter resultsConverter,
                                                                        final EligibilityDocumentConverter eligibilityConverter) {
        return MobilePinsentryDocumentConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .build();
    }

    @Bean
    public VerificationMethodDocumentConverter oneTimePasscodeSmsConverter(final VerificationResultsDocumentConverter resultsConverter,
                                                                           final EligibilityDocumentConverter eligibilityConverter,
                                                                           final MobileNumbersDocumentConverter mobileNumbersConverter,
                                                                           final PasscodeSettingsDocumentConverter passcodeSettingsConverter) {
        return OneTimePasscodeSmsDocumentConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .mobileNumbersConverter(mobileNumbersConverter)
                .passcodeSettingsConverter(passcodeSettingsConverter)
                .build();
    }

    @Bean
    public VerificationMethodDocumentConverter cardCredentialsConverter(final VerificationResultsDocumentConverter resultsConverter,
                                                                        final EligibilityDocumentConverter eligibilityConverter) {
        return CardCredentialsDocumentConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .build();
    }

    @Bean
    public VerificationMethodDocumentConverterDelegator methodConverterDelegator(final Collection<VerificationMethodDocumentConverter> methodConverters) {
        return new VerificationMethodDocumentConverterDelegator(methodConverters);
    }

    @Bean
    public VerificationMethodsDocumentConverter methodsConverter(final VerificationMethodDocumentConverterDelegator methodConverter) {
        return new VerificationMethodsDocumentConverter(methodConverter);
    }

    @Bean
    public VerificationSequenceDocumentConverter sequenceConverter(final VerificationMethodsDocumentConverter methodsConverter) {
        return new VerificationSequenceDocumentConverter(methodsConverter);
    }

    @Bean
    public VerificationSequencesConverter sequencesConverter(final VerificationSequenceDocumentConverter sequenceConverter) {
        return new VerificationSequencesConverter(sequenceConverter);
    }

    @Bean
    public VerificationContextDocumentConverter verificationContextConverter(final ChannelDocumentConverter channelConverter,
                                                                             final AliasDocumentConverter aliasConverter,
                                                                             final IdentityDocumentConverter identityConverter,
                                                                             final ActivityConverterDelegator activityConverter,
                                                                             final VerificationSequencesConverter sequencesConverter) {
        return VerificationContextDocumentConverter.builder()
                .channelConverter(channelConverter)
                .aliasConverter(aliasConverter)
                .identityConverter(identityConverter)
                .activityConverter(activityConverter)
                .sequencesConverter(sequencesConverter)
                .build();
    }

    @Bean
    public VerificationAttemptDocumentConverter verificationAttemptConverter(final AliasDocumentConverter aliasConverter) {
        return new VerificationAttemptDocumentConverter(aliasConverter);
    }

    @Bean
    public VerificationAttemptsDocumentConverter verificationAttemptsConverter(final VerificationAttemptDocumentConverter attemptConverter) {
        return new VerificationAttemptsDocumentConverter(attemptConverter);
    }

    @Bean
    public LockoutPolicyDocumentConverter mongoLockoutPolicyParametersDocumentConverter() {
        return new MaxAttemptsLockoutPolicyDocumentConverter();
    }

    @Bean
    public LockoutPolicyDocumentConverterDelegator lockoutPolicyParametersDocumentConverterDelegator(final Collection<LockoutPolicyDocumentConverter> converters) {
        return new LockoutPolicyDocumentConverterDelegator(converters);
    }

    @Bean
    public IdentityDao identityDao(final IdentityRepository repository,
                                   final IdentityDocumentConverter identityConverter) {
        return MongoIdentityDao.builder()
                .repository(repository)
                .converter(identityConverter)
                .build();
    }

    @Bean
    public VerificationContextDao verificationContextDao(final VerificationContextRepository repository,
                                                         final VerificationContextDocumentConverter contextConverter) {
        return MongoVerificationContextDao.builder()
                .repository(repository)
                .converter(contextConverter)
                .build();
    }

    @Bean
    public VerificationAttemptsDao verificationAttemptsDao(final VerificationAttemptsRepository repository,
                                                           final VerificationAttemptsDocumentConverter attemptsConverter) {
        return MongoVerificationAttemptsDao.builder()
                .repository(repository)
                .converter(attemptsConverter)
                .build();
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final LockoutPolicyRepository repository,
                                             final LockoutPolicyDocumentConverterDelegator documentConverter,
                                             final LockoutPolicyParametersConverter parametersConverter) {
        return MongoLockoutPolicyDao.builder()
                .repository(repository)
                .documentConverter(documentConverter)
                .parametersConverter(parametersConverter)
                .build();
    }

    @RequiredArgsConstructor
    private static class IndiciesResolverListener {

        private final MongoIndexResolver resolver;

        @EventListener(ApplicationReadyEvent.class)
        public void initIndicesAfterStartup() {
            resolver.resolve();
        }

    }

    private static Optional<ConnectionString> loadMongoConnectionString() {
        return Optional.ofNullable(System.getenv("MONGO_CONNECTION_STRING"))
                .map(ConnectionString::new);
    }

}
