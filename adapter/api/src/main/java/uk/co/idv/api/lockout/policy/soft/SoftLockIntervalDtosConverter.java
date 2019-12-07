package uk.co.idv.api.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;

import java.util.Collection;
import java.util.stream.Collectors;

public class SoftLockIntervalDtosConverter {

    private final SoftLockIntervalDtoConverter converter;

    public SoftLockIntervalDtosConverter(final SoftLockIntervalDtoConverter converter) {
        this.converter = converter;
    }

    public SoftLockIntervals toIntervals(final Collection<SoftLockIntervalDto> dtos) {
        final Collection<SoftLockInterval> intervals = dtos.stream()
                .map(this::toInterval)
                .collect(Collectors.toList());
        return new SoftLockIntervals(intervals);
    }

    public SoftLockInterval toInterval(final SoftLockIntervalDto dto) {
        return converter.toInterval(dto);
    }

    public Collection<SoftLockIntervalDto> toDtos(final SoftLockIntervals intervals) {
        return intervals.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public SoftLockIntervalDto toDto(final SoftLockInterval interval) {
        return converter.toDto(interval);
    }

}
