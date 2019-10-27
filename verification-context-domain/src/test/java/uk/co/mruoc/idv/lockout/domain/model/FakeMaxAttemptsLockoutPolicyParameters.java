package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class FakeMaxAttemptsLockoutPolicyParameters extends MaxAttemptsLockoutPolicyParameters {

    private static final int MAX_ATTEMPTS = 3;

    public FakeMaxAttemptsLockoutPolicyParameters() {
        super(UUID.fromString("39374b21-e603-4408-9155-d122e3c1222a"),
                RecordEveryAttempt.TYPE,
                Collections.singleton("fake-channel"),
                Collections.singleton("fake-activity"),
                Arrays.asList(CreditCardNumber.TYPE, DebitCardNumber.TYPE),
                MAX_ATTEMPTS);
    }

}
