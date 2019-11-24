package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervalMother;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;

import java.util.UUID;

public class LockoutPolicyMother {

    public static LockoutPolicy hardLockoutPolicy() {
        return hardLockoutPolicy(UUID.randomUUID());
    }

    public static LockoutPolicy hardLockoutPolicy(final LockoutLevel lockoutLevel) {
        return new HardLockoutPolicy(
                UUID.randomUUID(),
                lockoutLevel,
                new RecordEveryAttempt(),
                3
        );
    }

    public static LockoutPolicy hardLockoutPolicy(final UUID id) {
        return new HardLockoutPolicy(
                id,
                LockoutLevelMother.aliasLockoutLevel(),
                new RecordEveryAttempt(),
                3
        );
    }

    public static SoftLockoutPolicy softLockoutPolicy() {
        return softLockoutPolicy(UUID.randomUUID());
    }

    public static SoftLockoutPolicy softLockoutPolicy(UUID id) {
        return new SoftLockoutPolicy(
                id,
                LockoutLevelMother.aliasLockoutLevel(),
                new RecordEveryAttempt(),
                SoftLockIntervalMother.intervals()
        );
    }

    public static LockoutPolicy nonLockingPolicy() {
        return new NonLockingLockoutPolicy(UUID.randomUUID(), LockoutLevelMother.aliasLockoutLevel());
    }

}
