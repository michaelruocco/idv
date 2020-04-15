package uk.co.idv.domain.entities.card.number;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardNumberTest {

    private static final String TOKENIZED = "1234567890123456";
    private static final CardType TYPE = CardType.CREDIT;

    @Test
    void shouldReturnTokenizedValue() {
        final CardNumber cardNumber = new CardNumber(TOKENIZED, TYPE);

        assertThat(cardNumber.getTokenized()).isEqualTo(TOKENIZED);
    }

    @Test
    void shouldReturnType() {
        final CardNumber cardNumber = new CardNumber(TOKENIZED, TYPE);

        assertThat(cardNumber.getType()).isEqualTo(TYPE);
    }

}
