package uk.co.idv.dynamo.table;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

import java.util.Collection;
import java.util.Collections;

public class SimpleCreateTableRequest extends CreateTableRequest {

    private static final String ID = "id";

    public SimpleCreateTableRequest(final String tableName) {
        withTableName(tableName);
        withKeySchema(buildKeys());
        withAttributeDefinitions(buildAttributes());
        withProvisionedThroughput(new DefaultProvisionedThroughput());
    }

    private Collection<KeySchemaElement> buildKeys() {
        return Collections.singleton(new KeySchemaElement(ID, KeyType.HASH));
    }

    private Collection<AttributeDefinition> buildAttributes() {
        return Collections.singleton(new AttributeDefinition(ID, ScalarAttributeType.S));
    }

}
