package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters.LockoutPolicyParametersBuilder;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.RecordEveryAttempt;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class LockoutPolicyParametersMother {

    public static LockoutPolicyParameters fake() {
        return fakeBuilder().build();
    }

    public static MaxAttemptsLockoutPolicyParameters fakeMaxAttempts() {
        return new MaxAttemptsLockoutPolicyParameters(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"),
                RecordEveryAttempt.TYPE,
                Collections.singleton("fake-channel"),
                Collections.singleton("fake-activity"),
                Arrays.asList(CreditCardNumber.TYPE, DebitCardNumber.TYPE),
                3);
    }

    public static LockoutPolicyParametersBuilder fakeBuilder() {
        return LockoutPolicyParameters.builder()
                .id(UUID.randomUUID())
                .lockoutType("fake-lockout-type")
                .recordAttemptStrategyType("fake-record-attempt-type")
                .channelIds(Collections.singleton("fake-channel"))
                .activityNames(Collections.singleton("fake-activity"))
                .aliasTypes(Arrays.asList(CreditCardNumber.TYPE, DebitCardNumber.TYPE));
    }

}
