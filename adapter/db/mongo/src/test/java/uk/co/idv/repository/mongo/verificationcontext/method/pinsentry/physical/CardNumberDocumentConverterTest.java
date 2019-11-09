package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumberMother;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CreditCardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.DebitCardNumber;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class CardNumberDocumentConverterTest {

    private final CardNumberDocumentConverter converter = new CardNumberDocumentConverter();

    @Test
    void shouldThrowExceptionIfTypeIsInvalid() {
        final CardNumberDocument document = CardNumberDocumentMother.invalidType();

        final Throwable error = catchThrowable(() -> converter.toCardNumber(document));

        assertThat(error)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(document.getType());
    }

    @Test
    void shouldReturnCreditCardIfTypeIsCredit() {
        final CardNumberDocument document = CardNumberDocumentMother.credit();

        final CardNumber cardNumber = converter.toCardNumber(document);

        assertThat(cardNumber).isInstanceOf(CreditCardNumber.class);
    }

    @Test
    void shouldPopulateIdOnCreditCard() {
        final CardNumberDocument document = CardNumberDocumentMother.credit();

        final CardNumber cardNumber = converter.toCardNumber(document);

        assertThat(cardNumber.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateTokenizedOnCreditCard() {
        final CardNumberDocument document = CardNumberDocumentMother.credit();

        final CardNumber cardNumber = converter.toCardNumber(document);

        assertThat(cardNumber.getTokenized()).isEqualTo(document.getTokenized());
    }

    @Test
    void shouldReturnDebitCardIfTypeIsDebit() {
        final CardNumberDocument document = CardNumberDocumentMother.debit();

        final CardNumber cardNumber = converter.toCardNumber(document);

        assertThat(cardNumber).isInstanceOf(DebitCardNumber.class);
    }

    @Test
    void shouldPopulateIdOnDebitCard() {
        final CardNumberDocument document = CardNumberDocumentMother.debit();

        final CardNumber cardNumber = converter.toCardNumber(document);

        assertThat(cardNumber.getId()).isEqualTo(UUID.fromString(document.getId()));
    }

    @Test
    void shouldPopulateTokenizedOnDebitCard() {
        final CardNumberDocument document = CardNumberDocumentMother.debit();

        final CardNumber cardNumber = converter.toCardNumber(document);

        assertThat(cardNumber.getTokenized()).isEqualTo(document.getTokenized());
    }

    @Test
    void shouldPopulateIdOnDocument() {
        final CardNumber cardNumber = CardNumberMother.credit();

        final CardNumberDocument document = converter.toDocument(cardNumber);

        assertThat(document.getId()).isEqualTo(cardNumber.getId().toString());
    }

    @Test
    void shouldPopulateTokenizedOnDocument() {
        final CardNumber cardNumber = CardNumberMother.credit();

        final CardNumberDocument document = converter.toDocument(cardNumber);

        assertThat(document.getTokenized()).isEqualTo(cardNumber.getTokenized());
    }

    @Test
    void shouldPopulateTypeOnDocument() {
        final CardNumber cardNumber = CardNumberMother.credit();

        final CardNumberDocument document = converter.toDocument(cardNumber);

        assertThat(document.getType()).isEqualTo(cardNumber.getType().name());
    }

}
