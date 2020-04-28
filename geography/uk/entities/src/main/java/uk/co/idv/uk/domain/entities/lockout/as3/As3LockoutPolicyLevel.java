package uk.co.idv.uk.domain.entities.lockout.as3;

import uk.co.idv.domain.entities.activity.Login;
import uk.co.idv.domain.entities.policy.DefaultPolicyLevel;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.uk.domain.entities.channel.as3.As3;

import java.util.Collections;

public class As3LockoutPolicyLevel extends DefaultPolicyLevel {

    public As3LockoutPolicyLevel() {
        super(
                As3.ID,
                Collections.singleton(Login.NAME),
                Collections.singleton(PolicyLevel.ALL)
        );
    }

}
