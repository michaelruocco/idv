package uk.co.idv.api.lockout.policy;

import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes.HardLockoutPolicyAttributesBuilder;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

public class LockoutPolicyAttributesMother {

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

    private static LockoutLevel lockoutLevel() {
        return LockoutLevelMother.aliasLockoutLevel();
    }

}
