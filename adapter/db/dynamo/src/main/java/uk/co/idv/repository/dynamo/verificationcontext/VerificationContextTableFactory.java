package uk.co.idv.repository.dynamo.verificationcontext;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import uk.co.idv.repository.dynamo.DynamoTableFactory;

public class VerificationContextTableFactory extends DynamoTableFactory {

    public VerificationContextTableFactory(final AmazonDynamoDB amazonDynamoDB, final String environment) {
        super(amazonDynamoDB, new VerificationContextCreateTableRequestFactory(environment));
    }

}
