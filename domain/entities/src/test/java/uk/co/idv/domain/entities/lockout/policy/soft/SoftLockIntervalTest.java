package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockIntervalTest {

    private final int NUMBER_OF_ATTEMPTS = 3;
    private final Duration DURATION = Duration.ofMinutes(15);

    private final SoftLockInterval interval = new SoftLockInterval(NUMBER_OF_ATTEMPTS, DURATION);

    @Test
    void shouldReturnNumberOfAttempts() {
        assertThat(interval.getNumberOfAttempts()).isEqualTo(NUMBER_OF_ATTEMPTS);
    }

    @Test
    void shouldReturnDuration() {
        assertThat(interval.getDuration()).isEqualTo(DURATION);
    }

}
