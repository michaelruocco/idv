package uk.co.idv.api.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;

import java.time.Duration;

public class SoftLockIntervalDtoConverter {

    public SoftLockInterval toInterval(final SoftLockIntervalDto dto) {
        return new SoftLockInterval(dto.getNumberOfAttempts(), Duration.ofMillis(dto.getDuration()));
    }

    public SoftLockIntervalDto toDto(final SoftLockInterval interval) {
        return new SoftLockIntervalDto(interval.getNumberOfAttempts(), interval.getDuration().toMillis());
    }

}
