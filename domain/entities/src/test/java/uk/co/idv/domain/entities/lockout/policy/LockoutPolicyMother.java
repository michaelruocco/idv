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
        return hardLockoutPolicy(UUID.fromString("08027524-b8fd-4df0-946d-056eb98f508f"));
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
        return softLockoutPolicy(UUID.fromString("36cae4d6-d007-4517-8863-ac8f04cc700a"));
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
        return recurringSoftLockoutPolicy(UUID.fromString("1fc57cfa-6fff-411c-bc74-de71a938b30f"));
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
        return new NonLockingLockoutPolicy(
                UUID.fromString("3a6b7856-9909-4cf9-8f01-098890431ad1"),
                LockoutLevelMother.aliasLockoutLevel()
        );
    }

}
