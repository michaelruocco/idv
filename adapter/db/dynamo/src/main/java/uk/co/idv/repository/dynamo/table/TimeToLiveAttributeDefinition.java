package uk.co.idv.repository.dynamo.table;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class TimeToLiveAttributeDefinition extends AttributeDefinition {

    public TimeToLiveAttributeDefinition() {
        super("ttl", ScalarAttributeType.N);
    }

}
