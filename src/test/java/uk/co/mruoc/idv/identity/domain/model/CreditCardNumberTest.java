package uk.co.mruoc.idv.identity.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreditCardNumberTest {

    private static final String VALUE = "4929101234567890";

    @Test
    void shouldReturnType() {
        final Alias cardNumber = new CreditCardNumber(VALUE);

        assertThat(cardNumber.getType()).isEqualTo("credit-card-number");
    }

    @Test
    void shouldReturnValue() {
        final Alias cardNumber = new CreditCardNumber(VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void isCardNumber() {
        final Alias cardNumber = new CreditCardNumber(VALUE);

        assertThat(cardNumber.isCardNumber()).isTrue();
    }

}
