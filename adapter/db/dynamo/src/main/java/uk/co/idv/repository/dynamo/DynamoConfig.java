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
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.DynamoIdentityDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocumentConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingRepository;
import uk.co.idv.repository.dynamo.json.JacksonJsonConverter;
import uk.co.idv.repository.dynamo.verificationcontext.DynamoVerificationContextDao;

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

    private Optional<EndpointConfiguration> getEndpointConfiguration() {
        return Optional.ofNullable(endpointConfiguration);
    }

    public IdentityDao identityDao(final AliasMappingRepository repository) {
        final AliasConverter aliasConverter = aliasConverter();
        return DynamoIdentityDao.builder()
                .aliasConverter(aliasConverter)
                .documentConverter(new AliasMappingDocumentConverter(aliasConverter))
                .repository(repository)
                .build();
    }

    public VerificationContextDao verificationContextDao(final AmazonDynamoDB client,
                                                         final ObjectMapper mapper) {
        return DynamoVerificationContextDao.builder()
                .converter(new JacksonJsonConverter(mapper))
                .table(new DynamoDB(client).getTable(tablePrefix + "-verification-context"))
                .build();
    }

    public IdvTables idvTables(final AmazonDynamoDB dynamoDB, final DynamoDBMapper mapper) {
        return IdvTables.builder()
                .mapper(mapper)
                .tableCreator(new DynamoTableCreator(dynamoDB))
                .build();
    }

    private AliasConverter aliasConverter() {
        return new AliasConverter(aliasFactory());
    }

    private AliasFactory aliasFactory() {
        return new AliasFactory();
    }

}
