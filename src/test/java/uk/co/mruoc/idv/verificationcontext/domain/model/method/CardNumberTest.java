package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CardNumberTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String TOKENIZED = "1234567890123456";
    private static final String MASKED = "************3456";

    @Test
    void shouldReturnId() {
        final CardNumber cardNumber = new CardNumber(ID, TOKENIZED);

        assertThat(cardNumber.getId()).isEqualTo(ID);
    }

    @Test
    void shouldReturnTokenizedValue() {
        final CardNumber cardNumber = new CardNumber(ID, TOKENIZED);

        assertThat(cardNumber.getTokenized()).isEqualTo(TOKENIZED);
    }

    @Test
    void shouldReturnMaskedValue() {
        final CardNumber cardNumber = new CardNumber(ID, TOKENIZED);

        assertThat(cardNumber.getMasked()).isEqualTo(MASKED);
    }

    @Test
    void shouldPopulateRandomIdIfNotProvided() {
        final CardNumber cardNumber = new CardNumber(TOKENIZED);

        assertThat(cardNumber.getId()).isNotNull();
    }

}

