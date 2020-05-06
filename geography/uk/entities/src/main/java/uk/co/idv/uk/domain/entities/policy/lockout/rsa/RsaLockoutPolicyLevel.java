package uk.co.idv.uk.domain.entities.policy.lockout.rsa;

import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.domain.entities.policy.DefaultPolicyLevel;
import uk.co.idv.uk.domain.entities.channel.Rsa;

import java.util.Arrays;
import java.util.Collections;

public class RsaLockoutPolicyLevel extends DefaultPolicyLevel {

    public RsaLockoutPolicyLevel() {
        super(Rsa.ID,
                Collections.singleton(OnlinePurchase.NAME),
                Arrays.asList(CreditCardNumber.TYPE, DebitCardNumber.TYPE));
    }

}
