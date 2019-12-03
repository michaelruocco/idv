package uk.co.idv.beantest;

import org.meanbean.factories.FactoryCollection;
import org.meanbean.test.BeanTester;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;

import java.util.UUID;

public class ApiBeanTester extends BeanTester {

    public ApiBeanTester() {
        final FactoryCollection factories = getFactoryCollection();
        factories.addFactory(UUID.class, new UuidFactory());
        factories.addFactory(LockoutLevel.class, new LockoutLevelFactory());
    }

}
