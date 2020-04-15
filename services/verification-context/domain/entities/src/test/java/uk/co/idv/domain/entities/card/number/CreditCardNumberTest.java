package uk.co.idv.domain.entities.card.number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreditCardNumberTest {

    private static final String TOKENIZED = "1234567890123456";

    @Test
    void shouldReturnTokenizedValue() {
        final CardNumber cardNumber = new CreditCardNumber(TOKENIZED);

        assertThat(cardNumber.getTokenized()).isEqualTo(TOKENIZED);
    }

    @Test
    void shouldReturnCreditType() {
        final CardNumber cardNumber = new CreditCardNumber(TOKENIZED);

        assertThat(cardNumber.getType()).isEqualTo(CardType.CREDIT);
    }

}
