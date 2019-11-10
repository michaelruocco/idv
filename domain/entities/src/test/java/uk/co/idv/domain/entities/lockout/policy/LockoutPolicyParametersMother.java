package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.MaxAttemptsLockoutPolicyParameters.MaxAttemptsLockoutPolicyParametersBuilder;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordEveryAttempt;

import java.util.UUID;

public class LockoutPolicyParametersMother {

    public static MaxAttemptsLockoutPolicyParameters maxAttempts() {
        return maxAttemptsBuilder().build();
    }

    public static MaxAttemptsLockoutPolicyParametersBuilder maxAttemptsBuilder() {
        return MaxAttemptsLockoutPolicyParameters.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttemptStrategyType(RecordEveryAttempt.TYPE)
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
