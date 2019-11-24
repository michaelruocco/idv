package uk.co.idv.repository.mongo.lockout.policy.soft;

import java.time.Duration;

public class SoftLockIntervalDocumentMother {

    private SoftLockIntervalDocumentMother() {
        // utility class
    }

    public static SoftLockIntervalDocument oneAttempt() {
        return new SoftLockIntervalDocument(1, Duration.ofMinutes(1).toMillis());
    }

}
