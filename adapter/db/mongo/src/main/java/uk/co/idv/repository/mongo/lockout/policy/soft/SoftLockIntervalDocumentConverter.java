package uk.co.idv.repository.mongo.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockInterval;

import java.time.Duration;

public class SoftLockIntervalDocumentConverter {

    public SoftLockInterval toInterval(final SoftLockIntervalDocument document) {
        return new SoftLockInterval(
                document.getNumberOfAttempts(),
                Duration.ofMillis(document.getDuration())
        );
    }

    public SoftLockIntervalDocument toDocument(final SoftLockInterval interval) {
        return new SoftLockIntervalDocument(
                interval.getNumberOfAttempts(),
                interval.getDuration().toMillis()
        );
    }

}
