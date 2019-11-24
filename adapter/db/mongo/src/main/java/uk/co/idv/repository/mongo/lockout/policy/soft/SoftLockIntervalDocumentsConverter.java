package uk.co.idv.repository.mongo.lockout.policy.soft;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervals;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SoftLockIntervalDocumentsConverter {

    private final SoftLockIntervalDocumentConverter documentConverter;

    public SoftLockIntervals toIntervals(final Collection<SoftLockIntervalDocument> documents) {
        final Collection<SoftLockInterval> intervals = documents.stream()
                .map(this::toInterval)
                .collect(Collectors.toList());
        return new SoftLockIntervals(intervals);
    }

    public SoftLockInterval toInterval(final SoftLockIntervalDocument document) {
        return documentConverter.toInterval(document);
    }

    public Collection<SoftLockIntervalDocument> toDocuments(final SoftLockIntervals intervals) {
        return intervals.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
    }

    public SoftLockIntervalDocument toDocument(final SoftLockInterval interval) {
        return documentConverter.toDocument(interval);
    }

}
