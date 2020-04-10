package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import uk.co.idv.dynamo.table.DefaultProvisionedThroughput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LockoutPolicyCreateTableRequest extends CreateTableRequest {

    private static final String ID = "id";

    public LockoutPolicyCreateTableRequest(final String environment) {
        withTableName(toTableName(environment));
        withKeySchema(buildKeys());
        withAttributeDefinitions(buildAttributes());
        withGlobalSecondaryIndexes(new ChannelIdIndex());
        withProvisionedThroughput(new DefaultProvisionedThroughput());
    }

    private static Collection<KeySchemaElement> buildKeys() {
        return Collections.singleton(new KeySchemaElement(ID, KeyType.HASH));
    }

    private static Collection<AttributeDefinition> buildAttributes() {
        final Collection<AttributeDefinition> attributes = new ArrayList<>();
        attributes.add(new AttributeDefinition(ID, ScalarAttributeType.S));
        attributes.add(new AttributeDefinition("channelId", ScalarAttributeType.S));
        return attributes;
    }

    private static String toTableName(final String environment) {
        return environment + "-lockout-policy";
    }

}
