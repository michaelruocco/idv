package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordNever;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockIntervalMother;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.domain.entities.policy.PolicyLevelMother;
import uk.co.idv.domain.entities.policy.PolicyLevel;

import java.util.UUID;

public class LockoutPolicyMother {

    public static LockoutPolicy hardLockoutPolicy() {
        return hardLockoutPolicy(PolicyLevelMother.aliasPolicyLevel());
    }

    public static LockoutPolicy hardLockoutPolicy(final PolicyLevel policyLevel) {
        return DefaultLockoutPolicy.builder()
                .id(UUID.fromString("08027524-b8fd-4df0-946d-056eb98f508f"))
                .level(policyLevel)
                .recordAttemptStrategy(new RecordEveryAttempt())
                .stateCalculator(new HardLockoutStateCalculator(3))
                .build();
    }

    public static LockoutPolicy softLockoutPolicy() {
        return DefaultLockoutPolicy.builder()
                .id(UUID.fromString("36cae4d6-d007-4517-8863-ac8f04cc700a"))
                .level(PolicyLevelMother.aliasPolicyLevel())
                .recordAttemptStrategy(new RecordEveryAttempt())
                .stateCalculator(new SoftLockoutStateCalculator(SoftLockIntervalMother.intervals()))
                .build();
    }

    public static LockoutPolicy recurringSoftLockoutPolicy() {
        return DefaultLockoutPolicy.builder()
                .id(UUID.fromString("1fc57cfa-6fff-411c-bc74-de71a938b30f"))
                .level(PolicyLevelMother.aliasPolicyLevel())
                .recordAttemptStrategy(new RecordEveryAttempt())
                .stateCalculator(new RecurringSoftLockoutStateCalculator(SoftLockIntervalMother.oneAttempt()))
                .build();
    }

    public static LockoutPolicy nonLockingPolicy() {
        return DefaultLockoutPolicy.builder()
                .id(UUID.fromString("3a6b7856-9909-4cf9-8f01-098890431ad1"))
                .level(PolicyLevelMother.aliasPolicyLevel())
                .recordAttemptStrategy(new RecordNever())
                .stateCalculator(new NonLockingStateCalculator())
                .build();
    }

}
