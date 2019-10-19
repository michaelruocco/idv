package uk.co.mruoc.idv.mongo;

import org.meanbean.lang.Factory;

import java.math.BigDecimal;

public class NumberFactory implements Factory<Number> {

    @Override
    public Number create() {
        return BigDecimal.ONE;
    }

}
