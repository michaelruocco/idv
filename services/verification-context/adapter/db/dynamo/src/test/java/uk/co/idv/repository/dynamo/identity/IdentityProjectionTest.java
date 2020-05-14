package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityProjectionTest {

    private final Projection projection = new IdentityProjection();

    @Test
    void shouldProjectBody() {
        assertThat(projection.getProjectionType()).isEqualTo(ProjectionType.INCLUDE.name());
        assertThat(projection.getNonKeyAttributes()).containsExactly("body");
    }

}
