package uk.co.idv.domain.entities.card.number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DebitCardNumberTest {

    private static final String TOKENIZED = "1234567890123456";

    @Test
    void shouldReturnTokenizedValue() {
        final CardNumber cardNumber = new DebitCardNumber(TOKENIZED);

        assertThat(cardNumber.getTokenized()).isEqualTo(TOKENIZED);
    }

    @Test
    void shouldReturnCreditType() {
        final CardNumber cardNumber = new DebitCardNumber(TOKENIZED);

        assertThat(cardNumber.getType()).isEqualTo(DebitCardNumber.TYPE);
    }

}
