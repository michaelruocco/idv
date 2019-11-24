package uk.co.idv.repository.mongo.lockout.policy.soft;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

public class SoftLockIntervalDocumentMother {

    private SoftLockIntervalDocumentMother() {
        // utility class
    }

    public static Collection<SoftLockIntervalDocument> collection(final int size) {
        final Collection<SoftLockIntervalDocument> documents = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            documents.add(build(i));
        }
        return documents;
    }

    public static SoftLockIntervalDocument oneAttempt() {
        return build(1);
    }

    public static SoftLockIntervalDocument build(int numberOfAttempts) {
        return new SoftLockIntervalDocument(numberOfAttempts, Duration.ofMinutes(numberOfAttempts).toMillis());
    }

}
