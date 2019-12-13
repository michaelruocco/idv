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
import uk.co.idv.repository.dynamo.AwsEnvironmentVariables;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.DynamoConfig.DynamoConfigBuilder;
import uk.co.idv.repository.dynamo.identity.IdentityRepository;
import uk.co.idv.repository.dynamo.identity.TableCreator;

@Configuration
@EnableDynamoDBRepositories(basePackages = "uk.co.idv.repository.dynamo")
@Profile("!stub")
public class DynamoDaoConfig {

    private final DynamoConfig config = buildConfig();

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return config.amazonDynamoDB();
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        return config.dynamoDBMapperConfig();
    }

    @Bean
    public IdentityDao identityDao(final IdentityRepository identityRepository) {
        return config.identityDao(identityRepository);
    }

    @Bean
    public TableCreator tableCreator(final AmazonDynamoDB dynamoClient,
                                     final DynamoDBMapper mapper) {
        return TableCreator.builder()
                .amazonDynamoDB(dynamoClient)
                .mapper(mapper)
                .build();
    }

    @Bean
    public CreateTablesListener createTablesListener(final TableCreator tableCreator) {
        return new CreateTablesListener(tableCreator);
    }

    private static DynamoConfig buildConfig() {
        final DynamoConfigBuilder configBuilder = DynamoConfig.builder()
                .region(AwsEnvironmentVariables.loadRegion());
        AwsEnvironmentVariables.loadDynamoDbEndpointConfiguration().ifPresent(configBuilder::endpointConfiguration);
        return configBuilder.build();
    }

    @RequiredArgsConstructor
    private static class CreateTablesListener {

        private final TableCreator tableCreator;

        @EventListener(ApplicationReadyEvent.class)
        public void createTablesAfterStartup() {
            tableCreator.create();
        }

    }

}
