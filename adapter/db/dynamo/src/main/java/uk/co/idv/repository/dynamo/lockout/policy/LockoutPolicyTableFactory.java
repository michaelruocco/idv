package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import uk.co.idv.repository.dynamo.DynamoTableFactory;

public class LockoutPolicyTableFactory extends DynamoTableFactory {

    public LockoutPolicyTableFactory(final AmazonDynamoDB amazonDynamoDB, final String environment) {
        super(amazonDynamoDB, new LockoutPolicyCreateTableRequestFactory(environment));
    }

}
