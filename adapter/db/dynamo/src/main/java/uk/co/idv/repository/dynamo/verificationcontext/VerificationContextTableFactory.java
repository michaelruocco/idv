package uk.co.idv.repository.dynamo.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import uk.co.idv.repository.dynamo.DynamoTableFactory;
import uk.co.idv.repository.dynamo.lockout.attempt.VerificationAttemptCreateTableRequestFactory;

public class VerificationContextTableFactory extends DynamoTableFactory {

    public VerificationContextTableFactory(final AmazonDynamoDB amazonDynamoDB, final String environment) {
        super(amazonDynamoDB, new VerificationAttemptCreateTableRequestFactory(environment));
    }

}
