package uk.co.idv.api.lockout.policy.soft;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockIntervalDtoConverterTest {

    private final SoftLockIntervalDtoConverter converter = new SoftLockIntervalDtoConverter();

    @Test
    void shouldConvertNumberOfAttemptsToInterval() {
        final SoftLockIntervalDto dto = new SoftLockIntervalDto(1, 1000L);

        final SoftLockInterval interval = converter.toInterval(dto);

        assertThat(interval.getNumberOfAttempts()).isEqualTo(dto.getNumberOfAttempts());
    }

    @Test
    void shouldConvertDurationToInterval() {
        final Duration duration = Duration.ofMinutes(1);
        final SoftLockIntervalDto dto = new SoftLockIntervalDto(1, duration.toMillis());

        final SoftLockInterval interval = converter.toInterval(dto);

        assertThat(interval.getDuration()).isEqualTo(duration);
    }

    @Test
    void shouldConvertNumberOfAttemptsToDto() {
        final int numberOfAttempts = 1;
        final SoftLockInterval interval = new SoftLockInterval(numberOfAttempts, Duration.ofMinutes(1));

        final SoftLockIntervalDto dto = converter.toDto(interval);

        assertThat(dto.getNumberOfAttempts()).isEqualTo(interval.getNumberOfAttempts());
    }

    @Test
    void shouldConvertDurationToDto() {
        final Duration duration = Duration.ofMinutes(1);
        final SoftLockInterval interval = new SoftLockInterval(1, duration);

        final SoftLockIntervalDto dto = converter.toDto(interval);

        assertThat(dto.getDuration()).isEqualTo(duration.toMillis());
    }

}
