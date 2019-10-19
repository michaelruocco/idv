package uk.co.mruoc.idv.mongo.dao.activity;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MonetaryAmountConverterTest {

    private static final Number NUMBER = BigDecimal.ONE;
    private static final String CURRENCY_CODE = "GBP";

    private final MonetaryAmountConverter converter = new MonetaryAmountConverter();

    @Test
    void shouldConvertNumberToAmount() {
        final MonetaryAmountDocument document = buildDocument();

        final MonetaryAmount amount = converter.toMonetaryAmount(document);

        assertThat(amount.getNumber().doubleValue()).isEqualTo(document.getNumber().doubleValue());
    }

    @Test
    void shouldConvertCurrencyCodeToAmount() {
        final MonetaryAmountDocument document = buildDocument();

        final MonetaryAmount amount = converter.toMonetaryAmount(document);

        assertThat(amount.getCurrency().getCurrencyCode()).isEqualTo(document.getCurrencyCode());
    }

    @Test
    void shouldConvertNumberToDocument() {
        final MonetaryAmount amount = buildAmount();

        final MonetaryAmountDocument document = converter.toDocument(amount);

        assertThat(document.getNumber().doubleValue()).isEqualTo(amount.getNumber().doubleValue());
    }


    @Test
    void shouldConvertCurrencyCodeToDocument() {
        final MonetaryAmount amount = buildAmount();

        final MonetaryAmountDocument document = converter.toDocument(amount);

        assertThat(document.getCurrencyCode()).isEqualTo(amount.getCurrency().getCurrencyCode());
    }

    private static MonetaryAmountDocument buildDocument() {
        final MonetaryAmountDocument document = new MonetaryAmountDocument();
        document.setNumber(NUMBER);
        document.setCurrencyCode(CURRENCY_CODE);
        return document;
    }

    private static MonetaryAmount buildAmount() {
        return Money.of(NUMBER, CURRENCY_CODE);
    }

}
