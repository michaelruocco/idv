package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

public class LockoutPolicyMother {

    public static LockoutPolicy hardLockoutPolicy() {
        return hardLockoutPolicy(UUID.randomUUID());
    }

    public static LockoutPolicy hardLockoutPolicy(final UUID id) {
        return new HardLockoutPolicy(
                id,
                LockoutLevelMother.aliasLockoutLevel(),
                new RecordEveryAttempt(),
                3
        );
    }

}
