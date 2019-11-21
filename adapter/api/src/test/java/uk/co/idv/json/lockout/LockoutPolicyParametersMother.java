package uk.co.idv.json.lockout;

import uk.co.idv.domain.entities.lockout.policy.AliasLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;
import uk.co.idv.json.lockout.HardLockoutPolicyParameters.HardLockoutPolicyParametersBuilder;

import java.util.UUID;

public class LockoutPolicyParametersMother {

    public static HardLockoutPolicyParameters hardLock() {
        return hardLockBuilder().build();
    }

    public static HardLockoutPolicyParametersBuilder hardLockBuilder() {
        return HardLockoutPolicyParameters.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttempts(RecordEveryAttempt.TYPE)
                .lockoutLevel(lockoutLevel())
                .maxNumberOfAttempts(3);
    }

    private static LockoutLevel lockoutLevel() {
        return AliasLockoutLevel.builder()
                .channelId("fake-channel")
                .activityName("fake-activity")
                .aliasType("fake-alias-type")
                .build();
    }

}
