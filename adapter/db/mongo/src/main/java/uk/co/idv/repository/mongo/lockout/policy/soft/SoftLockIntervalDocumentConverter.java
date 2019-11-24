package uk.co.idv.repository.mongo.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;

import java.time.Duration;
import java.util.Collection;
import java.util.stream.Collectors;

public class SoftLockIntervalDocumentConverter {

    public SoftLockIntervals toIntervals(final Collection<SoftLockIntervalDocument> documents) {
        final Collection<SoftLockInterval> intervals = documents.stream()
                .map(this::toInterval)
                .collect(Collectors.toList());
        return new SoftLockIntervals(intervals);
    }

    public SoftLockInterval toInterval(final SoftLockIntervalDocument document) {
        return new SoftLockInterval(
                document.getNumberOfAttempts(),
                Duration.ofMillis(document.getDuration())
        );
    }

    public Collection<SoftLockIntervalDocument> toDocuments(final SoftLockIntervals intervals) {
        return intervals.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
    }

    public SoftLockIntervalDocument toDocument(final SoftLockInterval interval) {
        return new SoftLockIntervalDocument(
                interval.getNumberOfAttempts(),
                interval.getDuration().toMillis()
        );
    }

}
