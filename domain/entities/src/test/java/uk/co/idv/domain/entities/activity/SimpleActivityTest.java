package uk.co.idv.domain.entities.activity;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleActivityTest {

    private static final String NAME = "activity-name";
    private static final Instant TIMESTAMP = Instant.now();

    private final Activity activity = new SimpleActivity(NAME, TIMESTAMP);

    @Test
    void shouldReturnName() {
        assertThat(activity.getName()).isEqualTo(NAME);
    }

    @Test
    void shouldReturnTimestamp() {
        assertThat(activity.getTimestamp()).isEqualTo(TIMESTAMP);
    }

}
