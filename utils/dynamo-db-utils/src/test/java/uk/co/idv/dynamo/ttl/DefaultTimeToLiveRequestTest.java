package uk.co.idv.dynamo.ttl;

import com.amazonaws.services.dynamodbv2.model.TimeToLiveSpecification;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultTimeToLiveRequestTest {

    private static final String TABLE_NAME = "table-name";

    private final DefaultTimeToLiveRequest request = new DefaultTimeToLiveRequest(TABLE_NAME);

    @Test
    void shouldSetTableName() {
        assertThat(request.getTableName()).isEqualTo(TABLE_NAME);
    }

    @Test
    void shouldSetAttributeNameOnSpecification() {
        final TimeToLiveSpecification specification = request.getTimeToLiveSpecification();

        assertThat(specification.getAttributeName()).isEqualTo("ttl");
    }

    @Test
    void shouldSetEnabledTrueOnSpecification() {
        final TimeToLiveSpecification specification = request.getTimeToLiveSpecification();

        assertThat(specification.getEnabled()).isTrue();
    }

}
