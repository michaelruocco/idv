package uk.co.idv.repository.dynamo.verification.onetimepasscode;

import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import org.junit.jupiter.api.Test;
import uk.co.idv.repository.dynamo.table.DefaultProvisionedThroughput;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OneTimePasscodeVerificationCreateTableRequestTest {

    private static final String ENVIRONMENT = "environment";

    private final CreateTableRequest request = new OneTimePasscodeVerificationCreateTableRequest(ENVIRONMENT);

    @Test
    void shouldSetTableNameWithEnvironmentPrefix() {
        assertThat(request.getTableName()).isEqualTo(ENVIRONMENT + "-one-time-passcode-verification");
    }

    @Test
    void shouldSetKeys() {
        final List<KeySchemaElement> keys = request.getKeySchema();

        assertThat(keys).containsExactly(new KeySchemaElement("id", KeyType.HASH));
    }

    @Test
    void shouldSetAttributes() {
        final List<AttributeDefinition> attributes = request.getAttributeDefinitions();

        assertThat(attributes).containsExactly(new AttributeDefinition("id", ScalarAttributeType.S));
    }

    @Test
    void shouldSetProvisionedThroughput() {
        final ProvisionedThroughput throughput = request.getProvisionedThroughput();

        assertThat(throughput).isInstanceOf(DefaultProvisionedThroughput.class);
    }

}
