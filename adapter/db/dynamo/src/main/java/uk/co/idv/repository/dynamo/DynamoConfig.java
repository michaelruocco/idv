package uk.co.idv.repository.dynamo;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.MultipleLockoutPoliciesHandler;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.identity.IdentityMappingTableFactory;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.DynamoIdentityDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingItemConverter;
import uk.co.idv.repository.dynamo.json.JsonConverter;
import uk.co.idv.repository.dynamo.lockout.attempt.DynamoVerificationAttemptDao;
import uk.co.idv.repository.dynamo.lockout.attempt.VerificationAttemptTableFactory;
import uk.co.idv.repository.dynamo.lockout.policy.DynamoLockoutPolicyDao;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyItemConverter;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyTableFactory;
import uk.co.idv.repository.dynamo.verificationcontext.DynamoVerificationContextDao;
import uk.co.idv.repository.dynamo.verificationcontext.VerificationContextTableFactory;

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

    public IdentityDao identityDao(final AmazonDynamoDB amazonDynamoDB) {
        final DynamoTableFactory tableFactory = new IdentityMappingTableFactory(amazonDynamoDB, environment);
        final AliasConverter aliasConverter = aliasConverter();
        return DynamoIdentityDao.builder()
                .aliasConverter(aliasConverter)
                .itemConverter(new AliasMappingItemConverter(aliasConverter))
                .table(tableFactory.createOrGetTable())
                .build();
    }

    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter, final AmazonDynamoDB amazonDynamoDB) {
        final DynamoTableFactory tableFactory = new LockoutPolicyTableFactory(amazonDynamoDB, environment);
        return DynamoLockoutPolicyDao.builder()
                .multiplePoliciesHandler(new MultipleLockoutPoliciesHandler())
                .converter(new LockoutPolicyItemConverter(jsonConverter))
                .table(tableFactory.createOrGetTable())
                .build();
    }

    public VerificationContextDao verificationContextDao(final JsonConverter jsonConverter,
                                                         final AmazonDynamoDB amazonDynamoDB) {
        final DynamoTableFactory tableFactory = new VerificationContextTableFactory(amazonDynamoDB, environment);
        return DynamoVerificationContextDao.builder()
                .converter(jsonConverter)
                .table(tableFactory.createOrGetTable())
                .build();
    }

    public VerificationAttemptDao verificationAttemptsDao(final JsonConverter jsonConverter,
                                                          final AmazonDynamoDB amazonDynamoDB) {
        final DynamoTableFactory tableFactory = new VerificationAttemptTableFactory(amazonDynamoDB, environment);
        return DynamoVerificationAttemptDao.builder()
                .converter(jsonConverter)
                .table(tableFactory.createOrGetTable())
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

}
