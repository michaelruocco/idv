package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

public class LockoutPolicyMother {

    public static LockoutPolicy maxAttemptsPolicy() {
        return maxAttemptsPolicy(UUID.randomUUID());
    }

    public static LockoutPolicy maxAttemptsPolicy(final UUID id) {
        return new MaxAttemptsLockoutPolicy(
                id,
                LockoutLevelMother.aliasLockoutLevel(),
                new RecordEveryAttempt(),
                3
        );
    }

}
