package uk.co.idv.repository.dynamo.identity;

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

class IdentityMappingCreateTableRequestTest {

    private static final String ENVIRONMENT = "environment";

    private final CreateTableRequest request = new IdentityMappingCreateTableRequest(ENVIRONMENT);

    @Test
    void shouldSetTableNameWithEnvironmentPrefix() {
        assertThat(request.getTableName()).isEqualTo(ENVIRONMENT + "-identity-mapping");
    }

    @Test
    void shouldSetKeys() {
        final List<KeySchemaElement> keys = request.getKeySchema();

        assertThat(keys).containsExactly(new KeySchemaElement("alias", KeyType.HASH));
    }

    @Test
    void shouldSetAttributes() {
        final List<AttributeDefinition> attributes = request.getAttributeDefinitions();

        assertThat(attributes).containsExactly(
                new AttributeDefinition("alias", ScalarAttributeType.S),
                new AttributeDefinition("idvId", ScalarAttributeType.S)
        );
    }

    @Test
    void shouldSetIdvIdIndex() {
        final List<GlobalSecondaryIndex> secondaryIndexes = request.getGlobalSecondaryIndexes();

        assertThat(secondaryIndexes).hasSize(1);
        assertThat(secondaryIndexes.get(0)).isInstanceOf(IdvIdIndex.class);
    }

    @Test
    void shouldSetProvisionedThroughput() {
        final ProvisionedThroughput throughput = request.getProvisionedThroughput();

        assertThat(throughput).isInstanceOf(DefaultProvisionedThroughput.class);
    }

}
