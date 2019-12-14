package uk.co.idv.repository.dynamo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.ConversionSchemas;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.repository.dynamo.identity.AliasConverter;
import uk.co.idv.repository.dynamo.identity.DynamoIdentityDao;
import uk.co.idv.repository.dynamo.identity.AliasMappingDocumentConverter;
import uk.co.idv.repository.dynamo.identity.AliasMappingRepository;

import java.util.Optional;

import static com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride.*;

@Builder
@Slf4j
public class DynamoConfig {

    @Builder.Default
    private final String tablePrefix = "dev";

    @Builder.Default
    private final Regions region = Regions.EU_WEST_1;

    @Builder.Default
    private final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    private final EndpointConfiguration endpointConfiguration;

    public static DynamoConfig standard() {
        final DynamoConfigBuilder configBuilder = DynamoConfig.builder()
                .region(AwsEnvironmentVariables.loadRegion());
        AwsEnvironmentVariables.loadDynamoDbEndpointConfiguration().ifPresent(configBuilder::endpointConfiguration);
        return configBuilder.build();
    }

    public AmazonDynamoDB amazonDynamoDB() {
        final AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider);
        final Optional<EndpointConfiguration> endpointConfiguration = getEndpointConfiguration();
        if (endpointConfiguration.isPresent()) {
            builder.withEndpointConfiguration(endpointConfiguration.get());
        } else {
            builder.withRegion(region);
        }
        return builder.build();
    }

    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        final TableNameOverride tableNameOverride = withTableNamePrefix(String.format("%s-", tablePrefix));
        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(tableNameOverride)
                .withConversionSchema(ConversionSchemas.V2)
                .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard())
                .build();
    }

    public DynamoIdentityDao identityDao(final AliasMappingRepository repository) {
        final AliasConverter aliasConverter = new AliasConverter(new AliasFactory());
        return DynamoIdentityDao.builder()
                .aliasConverter(aliasConverter)
                .documentConverter(new AliasMappingDocumentConverter(aliasConverter))
                .repository(repository)
                .build();
    }

    public IdvTables idvTables(final AmazonDynamoDB dynamoDB, final DynamoDBMapper mapper) {
        return IdvTables.builder()
                .mapper(mapper)
                .tableCreator(new DynamoTableCreator(dynamoDB))
                .build();
    }

    private Optional<EndpointConfiguration> getEndpointConfiguration() {
        return Optional.ofNullable(endpointConfiguration);
    }

}
