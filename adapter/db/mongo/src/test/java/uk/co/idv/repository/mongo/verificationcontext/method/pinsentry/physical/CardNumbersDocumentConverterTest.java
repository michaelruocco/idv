package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.method.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.CardNumberMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CardNumbersDocumentConverterTest {

    private final CardNumberDocument document1 = CardNumberDocumentMother.debit();
    private final CardNumberDocument document2 = CardNumberDocumentMother.credit();

    private final CardNumber cardNumber1 = CardNumberMother.debit();
    private final CardNumber cardNumber2 = CardNumberMother.credit();

    private final CardNumberDocumentConverter cardNumberConverter = mock(CardNumberDocumentConverter.class);

    private final CardNumbersDocumentConverter cardNumbersConverter = new CardNumbersDocumentConverter(cardNumberConverter);

    @Test
    void shouldConvertMultipleDocumentsToCardNumbers() {
        given(cardNumberConverter.toCardNumber(document1)).willReturn(cardNumber1);
        given(cardNumberConverter.toCardNumber(document2)).willReturn(cardNumber2);

        final Collection<CardNumber> cardNumbers = cardNumbersConverter.toCardNumbers(Arrays.asList(document1, document2));

        assertThat(cardNumbers).containsExactly(cardNumber1, cardNumber2);
    }

    @Test
    void shouldConvertMultipleCardNumbersToDocuments() {
        given(cardNumberConverter.toDocument(cardNumber1)).willReturn(document1);
        given(cardNumberConverter.toDocument(cardNumber2)).willReturn(document2);

        final Collection<CardNumberDocument> documents = cardNumbersConverter.toDocuments(Arrays.asList(cardNumber1, cardNumber2));

        assertThat(documents).containsExactly(document1, document2);
    }

}
