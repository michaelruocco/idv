package uk.co.idv.beantest;

import org.meanbean.lang.Factory;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.policy.LockoutLevelMother;

public class LockoutLevelFactory implements Factory<PolicyLevel> {

    @Override
    public PolicyLevel create() {
        return LockoutLevelMother.defaultLockoutLevel();
    }

}
