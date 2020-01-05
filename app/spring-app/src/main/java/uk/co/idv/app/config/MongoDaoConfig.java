package uk.co.idv.app.config;

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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
import uk.co.idv.domain.usecases.lockout.MultipleLockoutPoliciesHandler;
import uk.co.idv.repository.mongo.MongoIndexResolver;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverterDelegator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyRepository;
import uk.co.idv.repository.mongo.lockout.policy.MongoLockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@EnableMongoRepositories(
        basePackages = "uk.co.idv.repository.mongo",
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*IdentityRepository"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*VerificationContextRepository"),
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*LockoutPolicyRepository")
        }
)
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
        MongoConnectionString.load().ifPresent(settingsBuilder::applyConnectionString);
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
    public MultipleLockoutPoliciesHandler multipleLockoutPoliciesHandler() {
        return new MultipleLockoutPoliciesHandler();
    }

    /*@Bean
    public LockoutPolicyDao lockoutPolicyDao(final LockoutPolicyRepository repository,
                                             final LockoutPolicyDocumentConverterDelegator documentConverter,
                                             final MultipleLockoutPoliciesHandler multiplePoliciesHandler) {
        return MongoLockoutPolicyDao.builder()
                .repository(repository)
                .documentConverter(documentConverter)
                .multiplePoliciesHandler(multiplePoliciesHandler)
                .build();
    }*/

    @RequiredArgsConstructor
    private static class IndiciesResolverListener {

        private final MongoIndexResolver resolver;

        @EventListener(ApplicationReadyEvent.class)
        public void initIndicesAfterStartup() {
            resolver.resolve();
        }

    }

}
