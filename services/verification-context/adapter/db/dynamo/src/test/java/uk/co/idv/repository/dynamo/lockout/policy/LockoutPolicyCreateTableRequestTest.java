package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import org.junit.jupiter.api.Test;
import uk.co.idv.dynamo.table.DefaultProvisionedThroughput;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LockoutPolicyCreateTableRequestTest {

    private static final String ENVIRONMENT = "environment";

    private final CreateTableRequest request = new LockoutPolicyCreateTableRequest(ENVIRONMENT);

    @Test
    void shouldSetTableNameWithEnvironmentPrefix() {
        assertThat(request.getTableName()).isEqualTo(ENVIRONMENT + "-lockout-policy");
    }

    @Test
    void shouldSetKeys() {
        final List<KeySchemaElement> keys = request.getKeySchema();

        assertThat(keys).containsExactly(new KeySchemaElement("id", KeyType.HASH));
    }

    @Test
    void shouldSetAttributes() {
        final List<AttributeDefinition> attributes = request.getAttributeDefinitions();

        assertThat(attributes).containsExactly(
                new AttributeDefinition("id", ScalarAttributeType.S),
                new AttributeDefinition("channelId", ScalarAttributeType.S)
        );
    }

    @Test
    void shouldSetChannelIdIndex() {
        final List<GlobalSecondaryIndex> secondaryIndexes = request.getGlobalSecondaryIndexes();

        assertThat(secondaryIndexes).hasSize(1);
        assertThat(secondaryIndexes.get(0)).isInstanceOf(ChannelIdIndex.class);
    }

    @Test
    void shouldSetProvisionedThroughput() {
        final ProvisionedThroughput throughput = request.getProvisionedThroughput();

        assertThat(throughput).isInstanceOf(DefaultProvisionedThroughput.class);
    }

}
