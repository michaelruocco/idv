package uk.co.mruoc.idv.domain.service;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class CurrentTimeServiceTest {

    private final TimeService service = new CurrentTimeService();

    @Test
    void shouldReturnCurrentTime() {
        final Instant time1 = service.now();
        final Instant time2 = service.now();

        assertThat(time1).isBefore(time2);
    }

}
