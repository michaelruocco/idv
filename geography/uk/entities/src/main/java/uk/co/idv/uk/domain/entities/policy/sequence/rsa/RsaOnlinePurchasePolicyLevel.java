package uk.co.idv.uk.domain.entities.policy.sequence.rsa;

import uk.co.idv.domain.entities.activity.OnlinePurchase;
import uk.co.idv.domain.entities.policy.DefaultPolicyLevel;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.uk.domain.entities.channel.Rsa;

import java.util.Collections;

public class RsaOnlinePurchasePolicyLevel extends DefaultPolicyLevel {

    public RsaOnlinePurchasePolicyLevel() {
        super(Rsa.ID,
                Collections.singleton(OnlinePurchase.NAME),
                Collections.singleton(PolicyLevel.ALL));
    }

}
