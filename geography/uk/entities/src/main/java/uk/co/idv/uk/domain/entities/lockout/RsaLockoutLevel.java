package uk.co.idv.uk.domain.entities.lockout;

import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.identity.alias.CreditCardNumber;
import uk.co.idv.domain.entities.identity.alias.DebitCardNumber;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.uk.domain.entities.channel.Rsa;

import java.util.Arrays;
import java.util.Collections;

public class RsaLockoutLevel extends DefaultLockoutLevel {

    public RsaLockoutLevel() {
        super(Rsa.ID,
                Collections.singleton(OnlinePurchase.NAME),
                Arrays.asList(CreditCardNumber.TYPE, DebitCardNumber.TYPE));
    }

}
