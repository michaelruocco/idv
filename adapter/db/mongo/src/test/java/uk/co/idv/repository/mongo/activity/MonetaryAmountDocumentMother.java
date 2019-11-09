package uk.co.idv.repository.mongo.activity;

import java.math.BigDecimal;

public class MonetaryAmountDocumentMother {

    public static MonetaryAmountDocument build() {
        final MonetaryAmountDocument document = new MonetaryAmountDocument();
        document.setNumber(BigDecimal.ONE);
        document.setCurrencyCode("GBP");
        return document;
    }

}
