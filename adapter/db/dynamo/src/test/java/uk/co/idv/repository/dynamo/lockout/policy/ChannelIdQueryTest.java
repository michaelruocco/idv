package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class ChannelIdQueryTest {

    private static final String CHANNEL_ID = "channel-id";

    private final QuerySpec query = new ChannelIdQuery(CHANNEL_ID);

    @Test
    void shouldSetChannelIdKeyConditionExpression() {
        final String expression = query.getKeyConditionExpression();

        assertThat(expression).isEqualTo("channelId = :channelId");
    }

    @Test
    void shouldSetChannelIdValueMap() {
        final Map<String, Object> valueMap = query.getValueMap();

        assertThat(valueMap).containsExactly(entry(":channelId", CHANNEL_ID));
    }

}
