package uk.co.idv.domain.entities.lockout.policy.soft;

import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SoftLockIntervalsTest {

    private final SoftLockInterval interval1 = SoftLockIntervalMother.build(2);
    private final SoftLockInterval interval2 = SoftLockIntervalMother.build(4);

    private final SoftLockIntervals intervals = new SoftLockIntervals(interval1, interval2);

    @Test
    void shouldReturnDurationOfIntervalWithHighestNumberOfAttemptsIfGreaterThanLargestNumberOfAttempts() {
        final int numberOfAttempts = 5;

        final Optional<SoftLockInterval> interval = intervals.findInterval(numberOfAttempts);

        assertThat(interval.isPresent()).isTrue();
        assertThat(interval.get()).isEqualTo(interval2);
    }

    @Test
    void shouldReturnEmptyInternalIfNumberOfAttemptsDoesNotMatchInterval() {
        final int numberOfAttempts = 3;

        final Optional<SoftLockInterval> interval = intervals.findInterval(numberOfAttempts);

        assertThat(interval).isEmpty();
    }

    @Test
    void shouldReturnIntervalMatchingNumberOfAttempts() {
        final int numberOfAttempts = 2;

        final Optional<SoftLockInterval> interval = intervals.findInterval(numberOfAttempts);

        assertThat(interval.isPresent()).isTrue();
        assertThat(interval.get()).isEqualTo(interval1);
    }

    @Test
    void shouldReturnIntervalsAsStream() {
        final Stream<SoftLockInterval> stream = intervals.stream();

        assertThat(stream).containsExactly(
                interval1,
                interval2
        );
    }

}
