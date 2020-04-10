package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

class IdentityByIdvIdQueryTest {

    private static final String IDV_ID_VALUE = "idvIdValue";

    private final QuerySpec query = new IdentityByIdvIdQuery(IDV_ID_VALUE);

    @Test
    void shouldSetKeyConditionExpression() {
        final String expression = query.getKeyConditionExpression();

        assertThat(expression).isEqualTo("idvId = :idvId");
    }

    @Test
    void shouldSetValueMap() {
        final Map<String, Object> map = query.getValueMap();

        assertThat(map).containsExactly(entry(":idvId", IDV_ID_VALUE));
    }

}
