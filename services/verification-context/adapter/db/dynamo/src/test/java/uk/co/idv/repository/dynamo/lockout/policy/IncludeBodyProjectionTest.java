package uk.co.idv.repository.dynamo.lockout.policy;

import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class IncludeBodyProjectionTest {

    private final Projection projection = new IncludeBodyProjection();

    @Test
    void shouldSetTypeInclude() {
        final String type = projection.getProjectionType();

        assertThat(type).isEqualTo(ProjectionType.INCLUDE.name());
    }

    @Test
    void shouldIncludeBodyNonKeyAttribute() {
        final Collection<String> nonKeyAttributes = projection.getNonKeyAttributes();

        assertThat(nonKeyAttributes).containsExactly("body");
    }

}
