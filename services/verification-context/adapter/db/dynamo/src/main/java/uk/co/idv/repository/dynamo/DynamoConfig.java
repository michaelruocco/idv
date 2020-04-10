package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptDao;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.dynamo.table.DynamoTableCreator;
import uk.co.idv.dynamo.table.DynamoTableService;
import uk.co.idv.dynamo.ttl.DefaultTimeToLiveRequest;
import uk.co.idv.dynamo.ttl.DynamoTimeToLiveService;
import uk.co.idv.dynamo.ttl.EpochSecondProvider;
import uk.co.idv.dynamo.ttl.OneHourTimeToLiveCalculator;
import uk.co.idv.repository.dynamo.identity.IdentityMappingCreateTableRequest;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.DynamoIdentityDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingItemConverter;
import uk.co.idv.repository.dynamo.lockout.attempt.DynamoVerificationAttemptDao;
import uk.co.idv.repository.dynamo.lockout.attempt.VerificationAttemptTableRequest;
import uk.co.idv.repository.dynamo.lockout.policy.DynamoLockoutPolicyDao;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyCreateTableRequest;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyItemConverter;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyItemsConverter;
import uk.co.idv.repository.dynamo.onetimepasscode.OneTimePasscodeDynamoConfig;
import uk.co.idv.repository.dynamo.verificationcontext.DynamoVerificationContextDao;
import uk.co.idv.repository.dynamo.verificationcontext.VerificationContextCreateTableRequest;
import uk.co.idv.repository.dynamo.verificationcontext.VerificationContextItemConverter;
import uk.co.idv.utils.aws.system.AwsSystemProperties;
import uk.co.idv.utils.json.converter.JsonConverter;

@RequiredArgsConstructor
@Slf4j
public class DynamoConfig {

    private final String environment;
    private final DynamoTableService tableService;
    private final DynamoTimeToLiveService timeToLiveService;
    private final OneTimePasscodeDynamoConfig oneTimePasscodeDynamoConfig;

    public DynamoConfig(final AmazonDynamoDB client) {
        this(AwsSystemProperties.loadEnvironment(),
                new DynamoTableService(new DynamoTableCreator(client)),
                new DynamoTimeToLiveService(client),
                new OneTimePasscodeDynamoConfig(client)
        );
    }

    public IdentityDao identityDao() {
        final AliasConverter aliasConverter = aliasConverter();
        return DynamoIdentityDao.builder()
                .aliasConverter(aliasConverter)
                .itemConverter(new AliasMappingItemConverter(aliasConverter))
                .table(tableService.getOrCreateTable(new IdentityMappingCreateTableRequest(environment)))
                .build();
    }

    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter) {
        return DynamoLockoutPolicyDao.builder()
                .table(tableService.getOrCreateTable(new LockoutPolicyCreateTableRequest(environment)))
                .converter(new LockoutPolicyItemsConverter(new LockoutPolicyItemConverter(jsonConverter)))
                .build();
    }

    public VerificationContextDao verificationContextDao(final JsonConverter jsonConverter,
                                                         final EpochSecondProvider epochSecondProvider) {
        final CreateTableRequest createTableRequest = new VerificationContextCreateTableRequest(environment);
        final VerificationContextItemConverter itemConverter = verificationContextItemConverter(jsonConverter, epochSecondProvider);
        final VerificationContextDao dao = DynamoVerificationContextDao.builder()
                .itemConverter(itemConverter)
                .table(tableService.getOrCreateTable(createTableRequest))
                .build();
        timeToLiveService.updateTimeToLive(new DefaultTimeToLiveRequest(createTableRequest.getTableName()));
        return dao;
    }

    public OneTimePasscodeVerificationDao oneTimePasscodeVerificationDao(final JsonConverter jsonConverter,
                                                                         final EpochSecondProvider epochSecondProvider) {
        return oneTimePasscodeDynamoConfig.verificationDao(jsonConverter, epochSecondProvider);
    }


    public VerificationAttemptDao verificationAttemptDao(final JsonConverter jsonConverter) {
        return DynamoVerificationAttemptDao.builder()
                .converter(jsonConverter)
                .table(tableService.getOrCreateTable(new VerificationAttemptTableRequest(environment)))
                .build();
    }

    public void clearLockoutPolicies() {
        tableService.recreateTable(new LockoutPolicyCreateTableRequest(environment));
    }

    private VerificationContextItemConverter verificationContextItemConverter(final JsonConverter jsonConverter,
                                                                              final EpochSecondProvider epochSecondProvider) {
        return VerificationContextItemConverter.builder()
                .jsonConverter(jsonConverter)
                .timeToLiveCalculator(new OneHourTimeToLiveCalculator(epochSecondProvider))
                .build();
    }

    private AliasConverter aliasConverter() {
        return new AliasConverter(aliasFactory());
    }

    private AliasFactory aliasFactory() {
        return new AliasFactory();
    }

}
