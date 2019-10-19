package uk.co.mruoc.idv.mongo.dao.activity;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;

public class MonetaryAmountConverter {

    public MonetaryAmount toMonetaryAmount(final MonetaryAmountDocument document) {
        return Money.of(document.getNumber(), document.getCurrencyCode());
    }

    public MonetaryAmountDocument toDocument(final MonetaryAmount amount) {
        final MonetaryAmountDocument document = new MonetaryAmountDocument();
        document.setNumber(amount.getNumber());
        document.setCurrencyCode(amount.getCurrency().getCurrencyCode());
        return document;
    }

}
