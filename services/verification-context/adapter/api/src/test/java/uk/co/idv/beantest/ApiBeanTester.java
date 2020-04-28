package uk.co.idv.beantest;

import org.meanbean.factories.FactoryCollection;
import org.meanbean.test.BeanTester;
import uk.co.idv.domain.entities.policy.PolicyLevel;

import java.util.UUID;

public class ApiBeanTester extends BeanTester {

    public ApiBeanTester() {
        final FactoryCollection factories = getFactoryCollection();
        factories.addFactory(UUID.class, new UuidFactory());
        factories.addFactory(PolicyLevel.class, new LockoutLevelFactory());
    }

}
