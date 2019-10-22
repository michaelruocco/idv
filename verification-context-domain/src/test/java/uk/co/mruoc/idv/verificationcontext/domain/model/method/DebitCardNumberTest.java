package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class DebitCardNumberTest {

    private static final String TOKENIZED = "1234567890123456";
    private static final String MASKED = "************3456";

    @Test
    void shouldReturnId() {
        final UUID id = UUID.randomUUID();

        final CardNumber cardNumber = new DebitCardNumber(id, TOKENIZED);

        assertThat(cardNumber.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnTokenizedValue() {
        final CardNumber cardNumber = new DebitCardNumber(TOKENIZED);

        assertThat(cardNumber.getTokenized()).isEqualTo(TOKENIZED);
    }

    @Test
    void shouldReturnMaskedValue() {
        final CardNumber cardNumber = new DebitCardNumber(TOKENIZED);

        assertThat(cardNumber.getMasked()).isEqualTo(MASKED);
    }

    @Test
    void shouldPopulateRandomIdIfNotProvided() {
        final CardNumber cardNumber = new DebitCardNumber(TOKENIZED);

        assertThat(cardNumber.getId()).isNotNull();
    }

    @Test
    void shouldReturnCreditType() {
        final CardNumber cardNumber = new DebitCardNumber(TOKENIZED);

        assertThat(cardNumber.getType()).isEqualTo(CardType.DEBIT);
    }

}
