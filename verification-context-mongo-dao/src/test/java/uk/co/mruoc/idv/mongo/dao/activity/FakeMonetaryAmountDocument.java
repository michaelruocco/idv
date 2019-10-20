package uk.co.mruoc.idv.mongo.dao.activity;

import java.math.BigDecimal;

public class FakeMonetaryAmountDocument extends MonetaryAmountDocument {

    public FakeMonetaryAmountDocument() {
        setNumber(BigDecimal.ONE);
        setCurrencyCode("GBP");
    }

}
