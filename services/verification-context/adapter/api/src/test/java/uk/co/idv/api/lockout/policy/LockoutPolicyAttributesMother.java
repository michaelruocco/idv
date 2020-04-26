package uk.co.idv.api.lockout.policy;

import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes.HardLockoutPolicyAttributesBuilder;
import uk.co.idv.api.lockout.policy.soft.RecurringSoftLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.soft.SoftLockoutPolicyAttributes;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordNever;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervalMother;

import java.util.UUID;

public class LockoutPolicyAttributesMother {

    public static LockoutPolicyAttributes nonLocking() {
        return new DefaultLockoutPolicyAttributes(
                UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"),
                NonLockingLockoutStateCalculator.TYPE,
                RecordNever.TYPE,
                LockoutLevelMother.aliasLockoutLevel()
        );
    }

    public static HardLockoutPolicyAttributes hardLock() {
        return hardLockBuilder().build();
    }

    public static HardLockoutPolicyAttributesBuilder hardLockBuilder() {
        return HardLockoutPolicyAttributes.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttempts(RecordEveryAttempt.TYPE)
                .lockoutLevel(LockoutLevelMother.aliasLockoutLevel())
                .maxNumberOfAttempts(3);
    }

    public static SoftLockoutPolicyAttributes softLock() {
        return SoftLockoutPolicyAttributes.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttempts(RecordEveryAttempt.TYPE)
                .lockoutLevel(LockoutLevelMother.aliasLockoutLevel())
                .intervals(SoftLockIntervalMother.intervals())
                .build();
    }

    public static RecurringSoftLockoutPolicyAttributes recurringSoftLock() {
        return RecurringSoftLockoutPolicyAttributes.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttempts(RecordEveryAttempt.TYPE)
                .lockoutLevel(LockoutLevelMother.aliasLockoutLevel())
                .interval(SoftLockIntervalMother.oneAttempt())
                .build();
    }

}
