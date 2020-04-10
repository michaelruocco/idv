package uk.co.idv.domain.entities.identity.alias;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardNumberTest {

    private static final String VALUE = "4929101234567890";

    private final Alias cardNumber = new FakeCardNumber(VALUE);

    @Test
    void shouldReturnType() {
        assertThat(cardNumber.getType()).isEqualTo("fake-card-number");
    }

    @Test
    void shouldReturnValue() {
        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void isCardNumber() {
        assertThat(cardNumber.isCardNumber()).isTrue();
    }

    @Test
    void shouldPrintDetails() {
        final String details = cardNumber.toString();

        assertThat(details).isEqualTo("CardNumber(type=fake-card-number, value=4929101234567890)");
    }

    @Test
    void shouldTestEquals() {
        EqualsVerifier.forClass(FakeCardNumber.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    private static class FakeCardNumber extends CardNumber {

        private FakeCardNumber(final String value) {
            super("fake-card-number", value);
        }

    }

}
