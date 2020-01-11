package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import uk.co.idv.repository.dynamo.DefaultProvisionedThroughput;

public class IdvIdIndex extends GlobalSecondaryIndex {

    public static final String IDV_ID = "idvId";

    public IdvIdIndex() {
        withIndexName(IDV_ID + "Index");
        withKeySchema(new KeySchemaElement(IDV_ID, KeyType.HASH));
        withProjection(new KeysOnlyProjection());
        withProvisionedThroughput(new DefaultProvisionedThroughput());
    }

}
