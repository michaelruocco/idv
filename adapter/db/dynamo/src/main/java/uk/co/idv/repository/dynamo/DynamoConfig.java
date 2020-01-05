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
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.MultipleLockoutPoliciesHandler;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptsDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.DynamoIdentityDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocumentConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingRepository;
import uk.co.idv.repository.dynamo.json.JsonConverter;
import uk.co.idv.repository.dynamo.lockout.attempt.DynamoVerificationAttemptsDao;
import uk.co.idv.repository.dynamo.lockout.policy.DynamoLockoutPolicyDao;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyItemConverterDelegator;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyItemConverter;
import uk.co.idv.repository.dynamo.lockout.policy.hard.HardLockoutPolicyItemConverter;
import uk.co.idv.repository.dynamo.lockout.policy.nonlocking.NonLockingPolicyItemConverter;
import uk.co.idv.repository.dynamo.lockout.policy.soft.RecurringSoftLockoutPolicyItemConverter;
import uk.co.idv.repository.dynamo.lockout.policy.soft.SoftLockoutPolicyItemConverter;
import uk.co.idv.repository.dynamo.verificationcontext.DynamoVerificationContextDao;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Builder
@Slf4j
public class DynamoConfig {

    private final String environment;
    private final Regions region;
    private final EndpointConfiguration endpointConfiguration;

    @Builder.Default
    private final AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();

    public AmazonDynamoDB amazonDynamoDB() {
        return getEndpointConfiguration()
                .map(this::toDynamoDb)
                .orElseGet(() -> toDynamoDb(region.getName()));
    }

    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(TableNameOverride.withTableNamePrefix(toPrefix(environment)))
                .withConversionSchema(ConversionSchemas.V2)
                .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard())
                .build();
    }

    public IdentityDao identityDao(final AliasMappingRepository repository) {
        final AliasConverter aliasConverter = aliasConverter();
        return DynamoIdentityDao.builder()
                .aliasConverter(aliasConverter)
                .documentConverter(new AliasMappingDocumentConverter(aliasConverter))
                .repository(repository)
                .build();
    }

    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter, final IdvTables tables) {
        return DynamoLockoutPolicyDao.builder()
                .multiplePoliciesHandler(new MultipleLockoutPoliciesHandler())
                .converter(new LockoutPolicyItemConverterDelegator(lockoutPolicyItemConverters(jsonConverter)))
                .table(tables.getLockoutPolicies())
                .channelIdIndex(tables.getLockoutPoliciesChannelIdIndex())
                .build();
    }

    public VerificationContextDao verificationContextDao(final JsonConverter jsonConverter, final IdvTables tables) {
        return DynamoVerificationContextDao.builder()
                .converter(jsonConverter)
                .table(tables.getVerificationContext())
                .build();
    }

    public VerificationAttemptsDao verificationAttemptsDao(final JsonConverter jsonConverter,
                                                           final IdvTables tables) {
        return DynamoVerificationAttemptsDao.builder()
                .converter(jsonConverter)
                .table(tables.getVerificationAttempts())
                .build();
    }

    public IdvTables idvTables(final AmazonDynamoDB dynamoDB, final DynamoDBMapper mapper) {
        return IdvTables.builder()
                .environment(environment)
                .mapper(mapper)
                .amazonDynamoDB(dynamoDB)
                .build();
    }

    private Optional<EndpointConfiguration> getEndpointConfiguration() {
        return Optional.ofNullable(endpointConfiguration);
    }

    private AmazonDynamoDB toDynamoDb(final EndpointConfiguration endpointConfiguration) {
        return amazonDynamoDBClientBuilder()
                .withEndpointConfiguration(endpointConfiguration)
                .build();
    }

    private AmazonDynamoDB toDynamoDb(final String region) {
        return amazonDynamoDBClientBuilder()
                .withRegion(region)
                .build();
    }

    private AmazonDynamoDBClientBuilder amazonDynamoDBClientBuilder() {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(credentialsProvider);
    }

    private AliasConverter aliasConverter() {
        return new AliasConverter(aliasFactory());
    }

    private AliasFactory aliasFactory() {
        return new AliasFactory();
    }

    private Collection<LockoutPolicyItemConverter> lockoutPolicyItemConverters(final JsonConverter jsonConverter) {
        return Arrays.asList(
                new NonLockingPolicyItemConverter(jsonConverter),
                new HardLockoutPolicyItemConverter(jsonConverter),
                new SoftLockoutPolicyItemConverter(jsonConverter),
                new RecurringSoftLockoutPolicyItemConverter(jsonConverter)
        );
    }

    private static String toPrefix(final String environment) {
        return String.format("%s-", environment);
    }

}
