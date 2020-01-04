package uk.co.idv.app.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptsDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.DynamoConfigFactory;
import uk.co.idv.repository.dynamo.IdvTables;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingRepository;

@Configuration
@EnableDynamoDBRepositories(
        basePackages = "uk.co.idv.repository.dynamo",
        dynamoDBMapperConfigRef = "dynamoDBMapperConfig"
)
@Profile("!stub")
public class DynamoDaoConfig {

    private final DynamoConfig config = DynamoConfigFactory.standard();

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return config.amazonDynamoDB();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        return config.dynamoDBMapperConfig();
    }

    @Bean
    public IdvTables idvTables(final AmazonDynamoDB dynamoDB,
                               final DynamoDBMapper mapper) {
        return config.idvTables(dynamoDB, mapper);
    }

    @Bean
    public CreateTablesListener createTablesListener(final IdvTables tables) {
        return new CreateTablesListener(tables);
    }

    @Bean
    public IdentityDao identityDao(final AliasMappingRepository repository) {
        return config.identityDao(repository);
    }

    @Bean
    public VerificationContextDao verificationContextDao(@Qualifier("dynamoObjectMapper") final ObjectMapper mapper,
                                                         final IdvTables tables) {
        return config.verificationContextDao(mapper, tables);
    }

    @Bean
    public VerificationAttemptsDao verificationAttemptsDao(@Qualifier("dynamoObjectMapper") final ObjectMapper mapper,
                                                           final IdvTables tables) {
        return config.verificationAttemptsDao(mapper, tables);
    }

    @RequiredArgsConstructor
    private static class CreateTablesListener {

        private final IdvTables tables;

        @EventListener(ApplicationReadyEvent.class)
        public void createTablesAfterStartup() {
            tables.create();
        }

    }

}
