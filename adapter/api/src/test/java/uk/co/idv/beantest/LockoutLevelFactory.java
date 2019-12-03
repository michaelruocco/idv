package uk.co.idv.beantest;

import org.meanbean.lang.Factory;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevelMother;

public class LockoutLevelFactory implements Factory<LockoutLevel> {

    @Override
    public LockoutLevel create() {
        return LockoutLevelMother.defaultLockoutLevel();
    }

}
