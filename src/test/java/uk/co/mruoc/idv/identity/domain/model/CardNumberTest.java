package uk.co.mruoc.idv.identity.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardNumberTest {

    private static final String VALUE = "4929101234567890";

    @Test
    void shouldReturnType() {
        final Alias cardNumber = new FakeCardNumber(VALUE);

        assertThat(cardNumber.getType()).isEqualTo("fake-card-number");
    }

    @Test
    void shouldReturnValue() {
        final Alias cardNumber = new FakeCardNumber(VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void isCardNumber() {
        final Alias cardNumber = new FakeCardNumber(VALUE);

        assertThat(cardNumber.isCardNumber()).isTrue();
    }

    private static class FakeCardNumber extends CardNumber {

        private FakeCardNumber(final String value) {
            super("fake-card-number", value);
        }

    }

}
