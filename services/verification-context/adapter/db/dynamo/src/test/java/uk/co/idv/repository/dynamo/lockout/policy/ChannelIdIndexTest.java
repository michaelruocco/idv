package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.jupiter.api.Test;
import uk.co.idv.dynamo.table.DefaultProvisionedThroughput;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class ChannelIdIndexTest {

    private final GlobalSecondaryIndex index = new ChannelIdIndex();

    @Test
    void shouldSetIndexName() {
        final String name = index.getIndexName();

        assertThat(name).isEqualTo("channelIdIndex");
    }

    @Test
    void shouldSetKeys() {
        final Collection<KeySchemaElement> keys = index.getKeySchema();

        assertThat(keys).containsExactly(new KeySchemaElement("channelId", KeyType.HASH));
    }

    @Test
    void shouldSetIncludeBodyProjection() {
        final Projection projection = index.getProjection();

        assertThat(projection).isInstanceOf(IncludeBodyProjection.class);
    }

    @Test
    void shouldSetDefaultProvisionedThroughput() {
        final ProvisionedThroughput throughput = index.getProvisionedThroughput();

        assertThat(throughput).isInstanceOf(DefaultProvisionedThroughput.class);
    }

}
