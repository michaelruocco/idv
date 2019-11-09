package uk.co.idv.domain.entities.identity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DebitCardNumberTest {

    private static final String VALUE = "4929101234567890";

    @Test
    void shouldReturnType() {
        final Alias cardNumber = new DebitCardNumber(VALUE);

        assertThat(cardNumber.getType()).isEqualTo("debit-card-number");
    }

    @Test
    void shouldReturnValue() {
        final Alias cardNumber = new DebitCardNumber(VALUE);

        assertThat(cardNumber.getValue()).isEqualTo(VALUE);
    }

    @Test
    void isCardNumber() {
        final Alias cardNumber = new DebitCardNumber(VALUE);

        assertThat(cardNumber.isCardNumber()).isTrue();
    }

    @Test
    void shouldBeEqualIfAllValuesAreTheSame() {
        EqualsVerifier.forClass(DebitCardNumber.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

}
