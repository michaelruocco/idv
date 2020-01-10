package uk.co.idv.repository.dynamo.verificationcontext;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import uk.co.idv.repository.dynamo.CreateTableRequestFactory;

import java.util.Collections;

public class VerificationContextCreateTableRequestFactory implements CreateTableRequestFactory {

    private final String tableName;

    public VerificationContextCreateTableRequestFactory(final String environment) {
        this.tableName = environment + "-verification-context";
    }

    public CreateTableRequest build() {
        final KeySchemaElement key = new KeySchemaElement("id", KeyType.HASH);
        final AttributeDefinition id = new AttributeDefinition(key.getAttributeName(), ScalarAttributeType.S);

        return new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(Collections.singleton(key))
                .withAttributeDefinitions(Collections.singleton(id))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
    }

}