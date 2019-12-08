package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervalMother;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;

import java.util.UUID;

public class LockoutPolicyMother {

    public static HardLockoutPolicy hardLockoutPolicy() {
        return hardLockoutPolicy(UUID.randomUUID());
    }

    public static HardLockoutPolicy hardLockoutPolicy(final LockoutLevel lockoutLevel) {
        return new HardLockoutPolicy(
                UUID.randomUUID(),
                lockoutLevel,
                new RecordEveryAttempt(),
                3
        );
    }

    public static HardLockoutPolicy hardLockoutPolicy(final UUID id) {
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

    public static RecurringSoftLockoutPolicy recurringSoftLockoutPolicy() {
        return recurringSoftLockoutPolicy(UUID.randomUUID());
    }

    public static RecurringSoftLockoutPolicy recurringSoftLockoutPolicy(UUID id) {
        return new RecurringSoftLockoutPolicy(
                id,
                LockoutLevelMother.aliasLockoutLevel(),
                new RecordEveryAttempt(),
                SoftLockIntervalMother.oneAttempt()
        );
    }

    public static LockoutPolicy nonLockingPolicy() {
        return new NonLockingLockoutPolicy(UUID.randomUUID(), LockoutLevelMother.aliasLockoutLevel());
    }

}
