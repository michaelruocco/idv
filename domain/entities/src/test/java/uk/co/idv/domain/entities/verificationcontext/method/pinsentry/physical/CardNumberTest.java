package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CardNumberTest {

    private static final String TOKENIZED = "1234567890123456";
    private static final CardType TYPE = CardType.CREDIT;

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final CardNumber cardNumber = new CardNumber(id, TOKENIZED, TYPE);

        assertThat(cardNumber.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnTokenizedValue() {
        final CardNumber cardNumber = new CardNumber(TOKENIZED, TYPE);

        assertThat(cardNumber.getTokenized()).isEqualTo(TOKENIZED);
    }

    @Test
    void shouldPopulateRandomIdIfNotProvided() {
        final CardNumber cardNumber = new CardNumber(TOKENIZED, TYPE);

        assertThat(cardNumber.getId()).isNotNull();
    }

    @Test
    void shouldReturnType() {
        final CardNumber cardNumber = new CardNumber(TOKENIZED, TYPE);

        assertThat(cardNumber.getType()).isEqualTo(TYPE);
    }

}
