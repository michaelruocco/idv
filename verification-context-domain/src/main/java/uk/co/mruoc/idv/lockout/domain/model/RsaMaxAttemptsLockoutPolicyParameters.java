package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Getter;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;
import uk.co.mruoc.idv.domain.model.channel.Rsa;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
import uk.co.mruoc.idv.identity.domain.model.DebitCardNumber;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Getter
public class RsaMaxAttemptsLockoutPolicyParameters extends MaxAttemptsLockoutPolicyParameters {

    private static final int MAX_ATTEMPTS = 3;

    public RsaMaxAttemptsLockoutPolicyParameters() {
        this(UUID.randomUUID());
    }

    public RsaMaxAttemptsLockoutPolicyParameters(final UUID id) {
        super(id,
                RecordEveryAttempt.TYPE,
                Collections.singleton(Rsa.ID),
                Collections.singleton(OnlinePurchase.NAME),
                Arrays.asList(CreditCardNumber.TYPE, DebitCardNumber.TYPE),
                MAX_ATTEMPTS);
    }

}