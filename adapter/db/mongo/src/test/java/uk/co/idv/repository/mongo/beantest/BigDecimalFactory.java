package uk.co.idv.repository.mongo.beantest;

import org.meanbean.lang.Factory;

import java.math.BigDecimal;

public class BigDecimalFactory implements Factory<BigDecimal> {

    @Override
    public BigDecimal create() {
        return BigDecimal.ONE;
    }

}