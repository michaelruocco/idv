package uk.co.idv.repository.mongo.activity;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.MonetaryAmount;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MonetaryAmountDocumentConverterTest {

    private final MonetaryAmountDocumentConverter converter = new MonetaryAmountDocumentConverter();

    @Test
    void shouldConvertNumberToAmount() {
        final MonetaryAmountDocument document = MonetaryAmountDocumentMother.build();

        final MonetaryAmount amount = converter.toMonetaryAmount(document);

        assertThat(amount.getNumber().doubleValue()).isEqualTo(document.getNumber().doubleValue());
    }

    @Test
    void shouldConvertCurrencyCodeToAmount() {
        final MonetaryAmountDocument document = MonetaryAmountDocumentMother.build();

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

    private static MonetaryAmount buildAmount() {
        return Money.of(BigDecimal.ONE, "GBP");
    }

}
