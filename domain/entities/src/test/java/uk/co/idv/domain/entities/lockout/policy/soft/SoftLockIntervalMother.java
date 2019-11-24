package uk.co.idv.domain.entities.lockout.policy.soft;

import java.time.Duration;

public class SoftLockIntervalMother {

    private SoftLockIntervalMother() {
        // utility class
    }

    public static SoftLockInterval oneAttempt() {
        return build(1);
    }

    public static SoftLockInterval build(int numberOfAttempts) {
        return new SoftLockInterval(numberOfAttempts, Duration.ofMinutes(numberOfAttempts));
    }

}
