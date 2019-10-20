package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CardNumbersConverter {

    private final CardNumberConverter cardNumberConverter;

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
