package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class CardNumberConverter {

    public Collection<CardNumber> toCardNumbers(final Collection<CardNumberDocument> documents) {
        return documents.stream()
                .map(this::toCardNumber)
                .collect(Collectors.toList());
    }

    public CardNumber toCardNumber(final CardNumberDocument document) {
        return new CardNumber(UUID.fromString(document.getId()), document.getTokenized());
    }

    public Collection<CardNumberDocument> toDocuments(final Collection<CardNumber> cardNumbers) {
        return cardNumbers.stream()
                .map(this::toDocument)
                .collect(Collectors.toList());
    }

    public CardNumberDocument toDocument(final CardNumber cardNumber) {
        final CardNumberDocument document = new CardNumberDocument();
        document.setId(cardNumber.getId().toString());
        document.setTokenized(cardNumber.getTokenized());
        return document;
    }

}
