package uk.co.idv.repository.dynamo;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.repository.dynamo.identity.IdentityMappingCreateTableRequestFactory;
import uk.co.idv.repository.dynamo.lockout.attempt.VerificationAttemptCreateTableRequestFactory;
import uk.co.idv.repository.dynamo.lockout.policy.LockoutPolicyCreateTableRequestFactory;
import uk.co.idv.repository.dynamo.verificationcontext.VerificationContextCreateTableRequestFactory;

@Slf4j
public class IdvTables {

    private final DynamoTableFactory verificationContextFactory;
    private final DynamoTableFactory verificationAttemptFactory;
    private final DynamoTableFactory lockoutPolicyFactory;
    private final DynamoTableFactory identityMappingFactory;

    public IdvTables(final AmazonDynamoDB amazonDynamoDB, final String environment) {
        this.verificationContextFactory = new DynamoTableFactory(amazonDynamoDB, new VerificationContextCreateTableRequestFactory(environment));
        this.verificationAttemptFactory = new DynamoTableFactory(amazonDynamoDB, new VerificationAttemptCreateTableRequestFactory(environment));
        this.lockoutPolicyFactory = new DynamoTableFactory(amazonDynamoDB, new LockoutPolicyCreateTableRequestFactory(environment));
        this.identityMappingFactory = new DynamoTableFactory(amazonDynamoDB, new IdentityMappingCreateTableRequestFactory(environment));
    }

    public Table getVerificationContext() {
        return verificationContextFactory.createOrGetTable();
    }

    public Table getVerificationAttempt() {
        return verificationAttemptFactory.createOrGetTable();
    }

    public Table getLockoutPolicy() {
        return lockoutPolicyFactory.createOrGetTable();
    }

    public Table getIdentity() {
        return identityMappingFactory.createOrGetTable();
    }

}
