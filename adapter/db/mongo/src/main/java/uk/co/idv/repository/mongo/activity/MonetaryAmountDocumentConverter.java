package uk.co.idv.repository.mongo.activity;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

public class MonetaryAmountDocumentConverter {

    public MonetaryAmount toMonetaryAmount(final MonetaryAmountDocument document) {
        return Money.of(document.getNumber(), document.getCurrencyCode());
    }

    public MonetaryAmountDocument toDocument(final MonetaryAmount amount) {
        final MonetaryAmountDocument document = new MonetaryAmountDocument();
        document.setNumber(amount.getNumber().numberValue(BigDecimal.class));
        document.setCurrencyCode(amount.getCurrency().getCurrencyCode());
        return document;
    }

}
