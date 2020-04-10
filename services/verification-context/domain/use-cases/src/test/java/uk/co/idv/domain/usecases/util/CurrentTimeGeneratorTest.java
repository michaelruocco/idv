package uk.co.idv.domain.usecases.util;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CurrentTimeGeneratorTest {

    private final TimeGenerator service = new CurrentTimeGenerator();

    @Test
    void shouldReturnCurrentTime() {
        final Instant time1 = service.now();
        final Instant time2 = service.now();

        assertThat(time1).isBefore(time2);
    }

}
