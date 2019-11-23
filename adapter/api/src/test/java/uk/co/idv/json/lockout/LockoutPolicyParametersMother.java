package uk.co.idv.json.lockout;

import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.json.lockout.HardLockoutPolicyDto.HardLockoutPolicyDtoBuilder;

import java.util.UUID;

public class LockoutPolicyParametersMother {

    public static HardLockoutPolicyDto hardLock() {
        return hardLockBuilder().build();
    }

    public static HardLockoutPolicyDtoBuilder hardLockBuilder() {
        return HardLockoutPolicyDto.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttempts(RecordEveryAttempt.TYPE)
                .lockoutLevel(lockoutLevel())
                .maxNumberOfAttempts(3);
    }

    private static LockoutLevel lockoutLevel() {
        return LockoutLevelMother.aliasLockoutLevel();
    }

}
