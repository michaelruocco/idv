package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class KeysOnlyProjectionTest {

    private final Projection projection = new KeysOnlyProjection();

    @Test
    void shouldOnlyProjectKeys() {
        assertThat(projection.getProjectionType()).isEqualTo(ProjectionType.KEYS_ONLY.name());
    }

}
