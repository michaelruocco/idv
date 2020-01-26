package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ChannelIdQuery extends QuerySpec {

    public ChannelIdQuery(final String channelId) {
        withKeyConditionExpression("channelId = :channelId");
        withValueMap(toValueMap(channelId));
    }

    private static ValueMap toValueMap(final String channelId) {
        return new ValueMap().withString(":channelId", channelId);
    }

}
