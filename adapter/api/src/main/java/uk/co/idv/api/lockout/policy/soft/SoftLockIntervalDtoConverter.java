package uk.co.idv.api.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;

public class SoftLockIntervalDtoConverter {

    public SoftLockIntervals toIntervals(final Collection<SoftLockIntervalDto> dtos) {
        final Collection<SoftLockInterval> intervals = dtos.stream()
                .map(this::toInterval)
                .collect(Collectors.toList());
        return new SoftLockIntervals(intervals);
    }

    public SoftLockInterval toInterval(final SoftLockIntervalDto dto) {
        return new SoftLockInterval(dto.getNumberOfAttempts(), Duration.ofMillis(dto.getDuration()));
    }

    public Collection<SoftLockIntervalDto> toDtos(final SoftLockIntervals intervals) {
        return intervals.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SoftLockIntervalDto toDto(final SoftLockInterval interval) {
        return new SoftLockIntervalDto(interval.getNumberOfAttempts(), interval.getDuration().toMillis());
    }

}
