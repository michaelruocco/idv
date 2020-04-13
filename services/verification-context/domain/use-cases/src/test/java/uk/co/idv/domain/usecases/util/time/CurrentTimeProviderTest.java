package uk.co.idv.domain.usecases.util.time;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CurrentTimeProviderTest {

    private final TimeProvider service = new CurrentTimeProvider();

    @Test
    void shouldReturnCurrentTime() {
        final Instant time1 = service.now();
        final Instant time2 = service.now();

        assertThat(time1).isBefore(time2);
    }

}
