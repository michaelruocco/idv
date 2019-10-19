package uk.co.mruoc.idv.mongo;

import org.meanbean.test.BeanTester;

public class CustomBeanTester extends BeanTester {

    public CustomBeanTester() {
        getFactoryCollection().addFactory(Number.class, new NumberFactory());
    }

}
