package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import uk.co.idv.repository.dynamo.CreateTableRequestFactory;

import java.util.Arrays;
import java.util.Collections;

public class LockoutPolicyCreateTableRequestFactory implements CreateTableRequestFactory {

    private final String tableName;

    public LockoutPolicyCreateTableRequestFactory(final String environment) {
        this.tableName = environment + "-lockout-policy";
    }

    public CreateTableRequest build() {
        final KeySchemaElement key = new KeySchemaElement("id", KeyType.HASH);
        final AttributeDefinition id = new AttributeDefinition(key.getAttributeName(), ScalarAttributeType.S);
        final AttributeDefinition channelId = new AttributeDefinition("channelId", ScalarAttributeType.S);

        return new CreateTableRequest()
                .withTableName(tableName)
                .withKeySchema(Collections.singleton(key))
                .withAttributeDefinitions(Arrays.asList(id, channelId))
                .withGlobalSecondaryIndexes(buildChannelIdIndex(channelId))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
    }

    private static GlobalSecondaryIndex buildChannelIdIndex(final AttributeDefinition channelId) {
        return new GlobalSecondaryIndex()
                .withIndexName("channelIdIndex")
                .withKeySchema(new KeySchemaElement(channelId.getAttributeName(), KeyType.HASH))
                .withProjection(new Projection().withProjectionType(ProjectionType.INCLUDE).withNonKeyAttributes("body"))
                .withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
    }

}
