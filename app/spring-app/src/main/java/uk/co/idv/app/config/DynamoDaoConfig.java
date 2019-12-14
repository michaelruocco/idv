package uk.co.idv.app.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import lombok.RequiredArgsConstructor;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.IdvTables;
import uk.co.idv.repository.dynamo.identity.AliasMappingRepository;

@Configuration
@EnableDynamoDBRepositories(
        basePackages = "uk.co.idv.repository.dynamo",
        dynamoDBMapperConfigRef = "dynamoDBMapperConfig"
)
@Profile("!stub")
public class DynamoDaoConfig {

    private final DynamoConfig config = DynamoConfig.standard();

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return config.amazonDynamoDB();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        return config.dynamoDBMapperConfig();
    }

    @Bean
    public IdentityDao identityDao(final AliasMappingRepository aliasMappingRepository) {
        return config.identityDao(aliasMappingRepository);
    }

    @Bean
    public IdvTables genericTableFactory(final AmazonDynamoDB dynamoDB,
                                         final DynamoDBMapper mapper) {
        return config.idvTables(dynamoDB, mapper);
    }

    @Bean
    public CreateTablesListener createTablesListener(final IdvTables tables) {
        return new CreateTablesListener(tables);
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
