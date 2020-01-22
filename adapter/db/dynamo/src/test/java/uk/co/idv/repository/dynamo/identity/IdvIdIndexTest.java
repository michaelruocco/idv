package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.dynamo.table.DefaultProvisionedThroughput;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class IdvIdIndexTest {

    private final GlobalSecondaryIndex index = new IdvIdIndex();

    @Test
    void shouldSetIndexName() {
        assertThat(index.getIndexName()).isEqualTo("idvIdIndex");
    }

    @Test
    void shouldSetKeys() {
        final Collection<KeySchemaElement> keys = index.getKeySchema();

        assertThat(keys).containsExactly(new KeySchemaElement("idvId", KeyType.HASH));
    }

    @Test
    void shouldSetKeysOnlyProjection() {
        final Projection projection = index.getProjection();

        assertThat(projection).isInstanceOf(KeysOnlyProjection.class);
    }

    @Test
    void shouldSetDefaultProvisionedThroughput() {
        final ProvisionedThroughput throughput = index.getProvisionedThroughput();

        assertThat(throughput).isInstanceOf(DefaultProvisionedThroughput.class);
    }

}
