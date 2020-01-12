package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.MultipleLockoutPoliciesHandler;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptDao;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.identity.IdentityMappingCreateTableRequest;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.DynamoIdentityDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingItemConverter;
import uk.co.idv.repository.dynamo.json.JsonConverter;
import uk.co.idv.repository.dynamo.lockout.attempt.DynamoVerificationAttemptDao;
import uk.co.idv.repository.dynamo.lockout.attempt.VerificationAttemptTableRequest;
import uk.co.idv.repository.dynamo.lockout.policy.DynamoLockoutPolicyDao;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyCreateTableRequest;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyItemConverter;
import uk.co.idv.repository.dynamo.table.DynamoTableCreator;
import uk.co.idv.repository.dynamo.table.DynamoTableService;
import uk.co.idv.repository.dynamo.table.DynamoTimeToLiveService;
import uk.co.idv.repository.dynamo.table.IdvTimeToLiveRequest;
import uk.co.idv.repository.dynamo.table.TimeToLiveCalculator;
import uk.co.idv.repository.dynamo.verificationcontext.DynamoVerificationContextDao;
import uk.co.idv.repository.dynamo.verificationcontext.VerificationContextCreateTableRequest;
import uk.co.idv.repository.dynamo.verificationcontext.VerificationContextItemConverter;
import uk.co.idv.repository.dynamo.verificationcontext.VerificationContextTimeToLiveCalculator;

@RequiredArgsConstructor
@Slf4j
public class DynamoConfig {

    private final String environment;
    private final DynamoTableService tableService;
    final DynamoTimeToLiveService timeToLiveService;

    public DynamoConfig(final AmazonDynamoDB client) {
        this(AwsSystemProperties.loadEnvironment(),
                new DynamoTableService(new DynamoTableCreator(client)),
                new DynamoTimeToLiveService(client)
        );
    }

    public IdentityDao identityDao() {
        final AliasConverter aliasConverter = aliasConverter();
        return DynamoIdentityDao.builder()
                .aliasConverter(aliasConverter)
                .itemConverter(new AliasMappingItemConverter(aliasConverter))
                .table(getOrCreateTable(new IdentityMappingCreateTableRequest(environment)))
                .build();
    }

    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter) {
        return DynamoLockoutPolicyDao.builder()
                .multiplePoliciesHandler(new MultipleLockoutPoliciesHandler())
                .converter(new LockoutPolicyItemConverter(jsonConverter))
                .table(getOrCreateTable(new LockoutPolicyCreateTableRequest(environment)))
                .build();
    }

    public VerificationContextDao verificationContextDao(final JsonConverter jsonConverter,
                                                         final TimeGenerator timeGenerator) {
        final CreateTableRequest createTableRequest = new VerificationContextCreateTableRequest(environment);
        final VerificationContextItemConverter itemConverter = itemConverter(jsonConverter, timeGenerator);
        final VerificationContextDao dao = DynamoVerificationContextDao.builder()
                .itemConverter(itemConverter)
                .table(getOrCreateTable(createTableRequest))
                .build();
        timeToLiveService.updateTimeToLive(new IdvTimeToLiveRequest(createTableRequest.getTableName()));
        return dao;
    }

    public VerificationAttemptDao verificationAttemptsDao(final JsonConverter jsonConverter) {
        return DynamoVerificationAttemptDao.builder()
                .converter(jsonConverter)
                .table(getOrCreateTable(new VerificationAttemptTableRequest(environment)))
                .build();
    }

    private VerificationContextItemConverter itemConverter(final JsonConverter jsonConverter,
                                                           final TimeGenerator timeGenerator) {
        final TimeToLiveCalculator timeToLiveCalculator = new VerificationContextTimeToLiveCalculator(timeGenerator);
        return VerificationContextItemConverter.builder()
                .jsonConverter(jsonConverter)
                .timeToLiveCalculator(timeToLiveCalculator)
                .build();
    }

    private Table getOrCreateTable(final CreateTableRequest request) {
        return tableService.getOrCreateTable(request);
    }

    private AliasConverter aliasConverter() {
        return new AliasConverter(aliasFactory());
    }

    private AliasFactory aliasFactory() {
        return new AliasFactory();
    }

}
