package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import uk.co.idv.dynamo.table.DefaultProvisionedThroughput;

public class ChannelIdIndex extends GlobalSecondaryIndex {

    public static final String CHANNEL_ID = "channelId";

    public ChannelIdIndex() {
        withIndexName(CHANNEL_ID + "Index");
        withKeySchema(new KeySchemaElement(CHANNEL_ID, KeyType.HASH));
        withProjection(new IncludeBodyProjection());
        withProvisionedThroughput(new DefaultProvisionedThroughput());
    }

}
