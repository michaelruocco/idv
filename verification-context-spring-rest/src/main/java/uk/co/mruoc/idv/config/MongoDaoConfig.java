package uk.co.mruoc.idv.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoDriverInformation;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.internal.MongoClientImpl;
import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;
import lombok.Builder;
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
import uk.co.mruoc.idv.mongo.dao.activity.ActivityConverter;
import uk.co.mruoc.idv.mongo.dao.activity.ActivityConverterDelegator;
import uk.co.mruoc.idv.mongo.dao.activity.MonetaryAmountConverter;
import uk.co.mruoc.idv.mongo.dao.activity.OnlinePurchaseConverter;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelConverter;
import uk.co.mruoc.idv.mongo.identity.dao.AliasConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityConverter;
import uk.co.mruoc.idv.mongo.identity.dao.IdentityRepository;
import uk.co.mruoc.idv.mongo.identity.dao.IndiciesResolver;
import uk.co.mruoc.idv.mongo.identity.dao.MongoIdentityDao;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.EligibilityConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.MongoVerificationContextDao;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.PushNotificationConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationContextConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationContextRepository;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.method.VerificationMethodConverterDelegator;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.result.VerificationResultConverter;
import uk.co.mruoc.idv.mongo.verificationcontext.dao.VerificationSequenceConverter;
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
    public IndiciesResolver indiciesResolver(final MongoTemplate template,
                                             final MongoMappingContext mappingContext) {
        return IndiciesResolver.builder()
                .template(template)
                .mappingContext(mappingContext)
                .build();
    }

    @Bean
    public IndiciesResolverListener indiciesResolverListener(final IndiciesResolver resolver) {
        return IndiciesResolverListener.builder()
                .resolver(resolver)
                .build();
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
        return OnlinePurchaseConverter.builder()
                .amountConverter(amountConverter)
                .build();
    }

    @Bean
    public ActivityConverterDelegator activityConverterDelegator(final Collection<ActivityConverter> activityConverters) {
        return new ActivityConverterDelegator(activityConverters);
    }

    @Bean
    public IdentityConverter identityConverter(final AliasConverter aliasConverter) {
        return new IdentityConverter(aliasConverter);
    }

    @Bean
    public AliasConverter aliasConverter(final AliasFactory aliasFactory) {
        return new AliasConverter(aliasFactory);
    }

    @Bean
    public VerificationResultConverter resultConverter() {
        return new VerificationResultConverter();
    }

    @Bean
    public EligibilityConverter eligibilityConverter() {
        return new EligibilityConverter();
    }

    @Bean
    public VerificationMethodConverter pushNotificationConverter(final VerificationResultConverter resultConverter,
                                                                 final EligibilityConverter eligibilityConverter) {
        return PushNotificationConverter.builder()
                .resultConverter(resultConverter)
                .eligibilityConverter(eligibilityConverter)
                .build();
    }

    @Bean
    public VerificationMethodConverterDelegator methodConverterDelegator(final Collection<VerificationMethodConverter> methodConverters) {
        return new VerificationMethodConverterDelegator(methodConverters);
    }

    @Bean
    public VerificationSequenceConverter sequenceConverter(final VerificationMethodConverterDelegator methodConverter) {
        return VerificationSequenceConverter.builder()
                .methodConverter(methodConverter)
                .build();
    }

    @Bean
    public VerificationContextConverter verificationContextConverter(final ChannelConverter channelConverter,
                                                                     final AliasConverter aliasConverter,
                                                                     final IdentityConverter identityConverter,
                                                                     final ActivityConverterDelegator activityConverter,
                                                                     final VerificationSequenceConverter sequenceConverter) {
        return VerificationContextConverter.builder()
                .channelConverter(channelConverter)
                .aliasConverter(aliasConverter)
                .identityConverter(identityConverter)
                .activityConverter(activityConverter)
                .sequenceConverter(sequenceConverter)
                .build();
    }

    @Bean
    public IdentityDao identityDao(final IdentityRepository repository,
                                   final IdentityConverter identityConverter) {
        return new MongoIdentityDao(repository, identityConverter);
    }

    @Bean
    public VerificationContextDao verificationContextDao(final VerificationContextRepository repository,
                                                         final VerificationContextConverter contextConverter) {
        return new MongoVerificationContextDao(repository, contextConverter);
    }

    @Builder
    private static class IndiciesResolverListener {

        private final IndiciesResolver resolver;

        @EventListener(ApplicationReadyEvent.class)
        public void initIndicesAfterStartup() {
            resolver.resolve();
        }

    }

}
