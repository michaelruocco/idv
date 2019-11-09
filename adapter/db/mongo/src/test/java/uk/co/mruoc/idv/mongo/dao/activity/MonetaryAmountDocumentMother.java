package uk.co.mruoc.idv.mongo.dao.activity;

import java.math.BigDecimal;

public class MonetaryAmountDocumentMother {

    public static MonetaryAmountDocument build() {
        final MonetaryAmountDocument document = new MonetaryAmountDocument();
        document.setNumber(BigDecimal.ONE);
        document.setCurrencyCode("GBP");
        return document;
    }

}
