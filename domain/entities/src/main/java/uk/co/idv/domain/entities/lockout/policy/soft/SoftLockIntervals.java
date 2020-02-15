package uk.co.idv.domain.entities.lockout.policy.soft;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode
public class SoftLockIntervals implements Iterable<SoftLockInterval> {

    private final SortedMap<Integer, SoftLockInterval> intervals = new TreeMap<>();

    public SoftLockIntervals(final SoftLockInterval... intervals) {
        this(Arrays.asList(intervals));
    }

    public SoftLockIntervals(final Collection<SoftLockInterval> intervals) {
        intervals.forEach(this::add);
    }

    @Override
    public Iterator<SoftLockInterval> iterator() {
        return intervals.values().iterator();
    }

    public Stream<SoftLockInterval> stream() {
        return intervals.values().stream();
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
