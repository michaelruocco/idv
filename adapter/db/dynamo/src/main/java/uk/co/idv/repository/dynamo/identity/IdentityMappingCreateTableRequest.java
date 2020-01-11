package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import uk.co.idv.repository.dynamo.DefaultProvisionedThroughput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class IdentityMappingCreateTableRequest extends CreateTableRequest {

    private static final String ALIAS = "alias";

    public IdentityMappingCreateTableRequest(final String environment) {
        withTableName(toTableName(environment));
        withKeySchema(buildKeys());
        withAttributeDefinitions(buildAttributes());
        withGlobalSecondaryIndexes(new IdvIdIndex());
        withProvisionedThroughput(new DefaultProvisionedThroughput());
    }

    private static String toTableName(final String environment) {
        return environment + "-identity-mapping";
    }

    private static Collection<KeySchemaElement> buildKeys() {
        return Collections.singleton(new KeySchemaElement(ALIAS, KeyType.HASH));
    }

    private static Collection<AttributeDefinition> buildAttributes() {
        final Collection<AttributeDefinition> attributes = new ArrayList<>();
        attributes.add(new AttributeDefinition(ALIAS, ScalarAttributeType.S));
        attributes.add(new AttributeDefinition("idvId", ScalarAttributeType.S));
        return attributes;
    }

}
