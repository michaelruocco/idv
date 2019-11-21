package uk.co.idv.domain.entities.lockout.policy.soft;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

public class SoftLockIntervals {

    private final SortedMap<Integer, SoftLockInterval> intervals = new TreeMap<>();

    public SoftLockIntervals(final SoftLockInterval... intervals) {
        this(Arrays.asList(intervals));
    }

    public SoftLockIntervals(final Collection<SoftLockInterval> intervals) {
        intervals.forEach(this::add);
    }

    public Optional<SoftLockInterval> findInterval(final int numberOfAttempts) {
        final int lastIntervalAttempts = intervals.lastKey();
        if (numberOfAttempts > lastIntervalAttempts) {
            return Optional.of(intervals.get(lastIntervalAttempts));
        }
        return Optional.ofNullable(intervals.get(numberOfAttempts));
    }

    private void add(final SoftLockInterval interval) {
        intervals.put(interval.getNumberOfAttempts(), interval);
    }

}
