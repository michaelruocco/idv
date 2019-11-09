package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.verificationcontext.method.CardNumber;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CardNumbersDocumentConverter {

    private final CardNumberDocumentConverter cardNumberConverter;

    public Collection<CardNumber> toCardNumbers(final Collection<CardNumberDocument> documents) {
        return documents.stream()
                .map(cardNumberConverter::toCardNumber)
                .collect(Collectors.toList());
    }

    public Collection<CardNumberDocument> toDocuments(final Collection<CardNumber> cardNumbers) {
        return cardNumbers.stream()
                .map(cardNumberConverter::toDocument)
                .collect(Collectors.toList());
    }

}
