package uk.co.idv.repository.dynamo.lockout.attempt;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import uk.co.idv.repository.dynamo.DynamoTableFactory;

public class VerificationAttemptTableFactory extends DynamoTableFactory {

    public VerificationAttemptTableFactory(final AmazonDynamoDB amazonDynamoDB, final String environment) {
        super(amazonDynamoDB, new VerificationAttemptCreateTableRequestFactory(environment));
    }

}
