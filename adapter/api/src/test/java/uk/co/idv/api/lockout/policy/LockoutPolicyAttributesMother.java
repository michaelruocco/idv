package uk.co.idv.api.lockout.policy;

import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes.HardLockoutPolicyAttributesBuilder;
import uk.co.idv.api.lockout.policy.soft.RecurringSoftLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.soft.SoftLockIntervalDto;
import uk.co.idv.api.lockout.policy.soft.SoftLockoutPolicyAttributes;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordNever;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class LockoutPolicyAttributesMother {

    public static LockoutPolicyAttributes nonLocking() {
        return new DefaultLockoutPolicyAttributes(
                UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"),
                NonLockingLockoutStateCalculator.TYPE,
                RecordNever.TYPE,
                lockoutLevel()
        );
    }

    public static HardLockoutPolicyAttributes hardLock() {
        return hardLockBuilder().build();
    }

    public static HardLockoutPolicyAttributesBuilder hardLockBuilder() {
        return HardLockoutPolicyAttributes.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttempts(RecordEveryAttempt.TYPE)
                .lockoutLevel(lockoutLevel())
                .maxNumberOfAttempts(3);
    }

    public static SoftLockoutPolicyAttributes softLock() {
        return SoftLockoutPolicyAttributes.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttempts(RecordEveryAttempt.TYPE)
                .lockoutLevel(lockoutLevel())
                .intervals(intervals())
                .build();
    }

    public static RecurringSoftLockoutPolicyAttributes recurringSoftLock() {
        return RecurringSoftLockoutPolicyAttributes.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttempts(RecordEveryAttempt.TYPE)
                .lockoutLevel(lockoutLevel())
                .interval(interval(1, Duration.ofMinutes(1)))
                .build();
    }

    private static LockoutLevel lockoutLevel() {
        return LockoutLevelMother.aliasLockoutLevel();
    }

    private static Collection<SoftLockIntervalDto> intervals() {
        return Arrays.asList(
                interval(1, Duration.ofMinutes(1)),
                interval(2, Duration.ofMinutes(2))
        );
    }

    private static SoftLockIntervalDto interval(final int numberOfAttempts, final Duration duration) {
        return new SoftLockIntervalDto(numberOfAttempts, duration.toMillis());
    }

}
