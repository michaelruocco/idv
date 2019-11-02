package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.RecordEveryAttempt;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class FakeLockoutPolicyParameters extends LockoutPolicyParameters {

    public FakeLockoutPolicyParameters() {
        this("fake-lockout-type");
    }

    public FakeLockoutPolicyParameters(final String lockoutType) {
        super(UUID.randomUUID(),
                lockoutType,
                RecordEveryAttempt.TYPE,
                Collections.singleton(Rsa.ID),
                Collections.singleton(OnlinePurchase.NAME),
                Arrays.asList(CreditCardNumber.TYPE, DebitCardNumber.TYPE));
    }

}
