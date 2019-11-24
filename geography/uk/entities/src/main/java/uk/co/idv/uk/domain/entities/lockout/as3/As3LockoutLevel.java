package uk.co.idv.uk.domain.entities.lockout.as3;

import uk.co.idv.domain.entities.activity.Login;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.uk.domain.entities.channel.as3.As3;

import java.util.Collections;

public class As3LockoutLevel extends DefaultLockoutLevel {

    public As3LockoutLevel() {
        super(
                As3.ID,
                Collections.singleton(Login.NAME),
                Collections.singleton(LockoutLevel.ALL)
        );
    }

}
