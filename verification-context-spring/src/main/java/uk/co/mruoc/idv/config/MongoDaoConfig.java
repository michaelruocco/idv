package uk.co.mruoc.idv.config;

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
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityConverter;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityConverterDelegator;
import uk.co.mruoc.idv.mongo.dao.activity.MonetaryAmountConverter;
import uk.co.mruoc.idv.mongo.dao.activity.OnlinePurchaseConverter;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasesConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityRepository;
import uk.co.mruoc.idv.mongo.identity.dao.MongoIndexResolver;
import uk.co.mruoc.idv.mongo.identity.dao.MongoIdentityDao;
import uk.co.mruoc.idv.mongo.lockout.dao.MongoVerificationAttemptsDao;
import uk.co.mruoc.idv.mongo.lockout.dao.VerificationAttemptConverter;
import uk.co.mruoc.idv.mongo.lockout.dao.VerificationAttemptsConverter;
import uk.co.mruoc.idv.mongo.lockout.dao.VerificationAttemptsRepository;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.MongoVerificationContextDao;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationSequencesConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.CardCredentialsConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.CardNumberConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.CardNumbersConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.MobileNumberConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.MobileNumbersConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.MobilePinsentryConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.OneTimePasscodeSmsConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PasscodeSettingsConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PhysicalPinsentryConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PushNotificationConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationContextConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationContextRepository;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodConverterDelegator;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodsConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationSequenceConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultsConverter;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
        final MongoClientSettings settings = MongoClientSettings.builder().build();
        final MongoDriverInformation driverInformation = MongoDriverInformation.builder().build();
        return new MongoClientImpl(settings, driverInformation);
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
    public ChannelConverter channelConverter() {
        return new ChannelConverter();
    }

    @Bean
    public MonetaryAmountConverter amountConverter() {
        return new MonetaryAmountConverter();
    }

    @Bean
    public OnlinePurchaseConverter onlinePurchaseConverter(final MonetaryAmountConverter amountConverter) {
        return new OnlinePurchaseConverter(amountConverter);
    }

    @Bean
    public ActivityConverterDelegator activityConverterDelegator(final Collection<ActivityConverter> activityConverters) {
        return new ActivityConverterDelegator(activityConverters);
    }

    @Bean
    public AliasConverter aliasConverter(final AliasFactory aliasFactory) {
        return new AliasConverter(aliasFactory);
    }

    @Bean
    public AliasesConverter aliasesConverter(final AliasConverter aliasConverter) {
        return new AliasesConverter(aliasConverter);
    }

    @Bean
    public IdentityConverter identityConverter(final AliasesConverter aliasesConverter) {
        return new IdentityConverter(aliasesConverter);
    }

    @Bean
    public VerificationResultConverter resultConverter() {
        return new VerificationResultConverter();
    }

    @Bean
    public VerificationResultsConverter resultsConverter(final VerificationResultConverter resultConverter) {
        return new VerificationResultsConverter(resultConverter);
    }

    @Bean
    public EligibilityConverter eligibilityConverter() {
        return new EligibilityConverter();
    }

    @Bean
    public CardNumberConverter cardNumberConverter() {
        return new CardNumberConverter();
    }

    @Bean
    public CardNumbersConverter cardNumbersConverter(final CardNumberConverter cardNumberConverter) {
        return new CardNumbersConverter(cardNumberConverter);
    }

    @Bean
    public MobileNumberConverter mobileNumberConverter() {
        return new MobileNumberConverter();
    }

    @Bean
    public MobileNumbersConverter mobileNumbersConverter(final MobileNumberConverter mobileNumberConverter) {
        return new MobileNumbersConverter(mobileNumberConverter);
    }

    @Bean
    public PasscodeSettingsConverter passcodeSettingsConverter() {
        return new PasscodeSettingsConverter();
    }

    @Bean
    public VerificationMethodConverter pushNotificationConverter(final VerificationResultsConverter resultsConverter,
                                                                 final EligibilityConverter eligibilityConverter) {
        return PushNotificationConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .build();
    }

    @Bean
    public VerificationMethodConverter physicalPinsentryConverter(final VerificationResultsConverter resultsConverter,
                                                                  final EligibilityConverter eligibilityConverter,
                                                                  final CardNumbersConverter cardNumbersConverter) {
        return PhysicalPinsentryConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .cardNumbersConverter(cardNumbersConverter)
                .build();
    }

    @Bean
    public VerificationMethodConverter mobilePinsentryConverter(final VerificationResultsConverter resultsConverter,
                                                                final EligibilityConverter eligibilityConverter) {
        return MobilePinsentryConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .build();
    }

    @Bean
    public VerificationMethodConverter oneTimePasscodeSmsConverter(final VerificationResultsConverter resultsConverter,
                                                                   final EligibilityConverter eligibilityConverter,
                                                                   final MobileNumbersConverter mobileNumbersConverter,
                                                                   final PasscodeSettingsConverter passcodeSettingsConverter) {
        return OneTimePasscodeSmsConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .mobileNumbersConverter(mobileNumbersConverter)
                .passcodeSettingsConverter(passcodeSettingsConverter)
                .build();
    }

    @Bean
    public VerificationMethodConverter cardCredentialsConverter(final VerificationResultsConverter resultsConverter,
                                                                final EligibilityConverter eligibilityConverter) {
        return CardCredentialsConverter.builder()
                .resultsConverter(resultsConverter)
                .eligibilityConverter(eligibilityConverter)
                .build();
    }

    @Bean
    public VerificationMethodConverterDelegator methodConverterDelegator(final Collection<VerificationMethodConverter> methodConverters) {
        return new VerificationMethodConverterDelegator(methodConverters);
    }

    @Bean
    public VerificationMethodsConverter methodsConverter(final VerificationMethodConverterDelegator methodConverter) {
        return new VerificationMethodsConverter(methodConverter);
    }

    @Bean
    public VerificationSequenceConverter sequenceConverter(final VerificationMethodsConverter methodsConverter) {
        return new VerificationSequenceConverter(methodsConverter);
    }

    @Bean
    public VerificationSequencesConverter sequencesConverter(final VerificationSequenceConverter sequenceConverter) {
        return new VerificationSequencesConverter(sequenceConverter);
    }

    @Bean
    public VerificationContextConverter verificationContextConverter(final ChannelConverter channelConverter,
                                                                     final AliasConverter aliasConverter,
                                                                     final IdentityConverter identityConverter,
                                                                     final ActivityConverterDelegator activityConverter,
                                                                     final VerificationSequencesConverter sequencesConverter) {
        return VerificationContextConverter.builder()
                .channelConverter(channelConverter)
                .aliasConverter(aliasConverter)
                .identityConverter(identityConverter)
                .activityConverter(activityConverter)
                .sequencesConverter(sequencesConverter)
                .build();
    }

    @Bean
    public VerificationAttemptConverter verificationAttemptConverter(final AliasConverter aliasConverter) {
        return new VerificationAttemptConverter(aliasConverter);
    }

    @Bean
    public VerificationAttemptsConverter verificationAttemptsConverter(final VerificationAttemptConverter attemptConverter) {
        return new VerificationAttemptsConverter(attemptConverter);
    }

    @Bean
    public IdentityDao identityDao(final IdentityRepository repository,
                                   final IdentityConverter identityConverter) {
        return MongoIdentityDao.builder()
                .repository(repository)
                .converter(identityConverter)
                .build();
    }

    @Bean
    public VerificationContextDao verificationContextDao(final VerificationContextRepository repository,
                                                         final VerificationContextConverter contextConverter) {
        return MongoVerificationContextDao.builder()
                .repository(repository)
                .converter(contextConverter)
                .build();
    }

    @Bean
    public VerificationAttemptsDao verificationAttemptsDao(final VerificationAttemptsRepository repository,
                                                           final VerificationAttemptsConverter attemptsConverter) {
        return MongoVerificationAttemptsDao.builder()
                .repository(repository)
                .converter(attemptsConverter)
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

}
