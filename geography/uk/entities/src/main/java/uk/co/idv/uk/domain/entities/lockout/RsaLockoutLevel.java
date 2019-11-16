package uk.co.idv.uk.domain.entities.lockout;

import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.lockout.policy.AliasLockoutLevel;
import uk.co.idv.uk.domain.entities.channel.Rsa;

public class RsaLockoutLevel extends AliasLockoutLevel {

    public RsaLockoutLevel(final String aliasType) {
        super(Rsa.ID, OnlinePurchase.NAME, aliasType);
    }

}
