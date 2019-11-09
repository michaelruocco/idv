package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.MaxAttemptsAliasLevelLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.MaxAttemptsAliasLevelLockoutPolicyParameters.MaxAttemptsAliasLevelLockoutPolicyParametersBuilder;
import uk.co.idv.domain.entities.lockout.RecordEveryAttempt;

import java.util.UUID;

public class LockoutPolicyParametersMother {

    public static MaxAttemptsAliasLevelLockoutPolicyParameters maxAttempts() {
        return maxAttemptsBuilder().build();
    }

    public static MaxAttemptsAliasLevelLockoutPolicyParametersBuilder maxAttemptsBuilder() {
        return MaxAttemptsAliasLevelLockoutPolicyParameters.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttemptStrategyType(RecordEveryAttempt.TYPE)
                .channelId("fake-channel")
                .activityName("fake-activity")
                .aliasType("fake-alias-type")
                .maxNumberOfAttempts(3);
    }

}
