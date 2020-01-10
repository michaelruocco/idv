package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import uk.co.idv.repository.dynamo.DynamoTableFactory;

public class IdentityMappingTableFactory extends DynamoTableFactory {

    public IdentityMappingTableFactory(final AmazonDynamoDB amazonDynamoDB, final String environment) {
        super(amazonDynamoDB, new IdentityMappingCreateTableRequestFactory(environment));
    }

}
