package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters.MaxAttemptsLockoutPolicyParametersBuilder;
import uk.co.mruoc.idv.lockout.domain.model.RecordEveryAttempt;

import java.util.Collections;
import java.util.UUID;

public class LockoutPolicyParametersMother {

    public static MaxAttemptsLockoutPolicyParameters maxAttempts() {
        return maxAttemptsBuilder().build();
    }

    public static MaxAttemptsLockoutPolicyParametersBuilder maxAttemptsBuilder() {
        return MaxAttemptsLockoutPolicyParameters.builder()
                .id(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"))
                .recordAttemptStrategyType(RecordEveryAttempt.TYPE)
                .channelIds(Collections.singleton("fake-channel"))
                .activityNames(Collections.singleton("fake-activity"))
                .aliasTypes(Collections.singleton("fake-alias-type"))
                .maxNumberOfAttempts(3);
    }

}
