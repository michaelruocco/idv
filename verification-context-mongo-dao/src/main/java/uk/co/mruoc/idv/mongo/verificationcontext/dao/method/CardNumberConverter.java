package uk.co.mruoc.idv.mongo.verificationcontext.dao.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;

import java.util.UUID;

public class CardNumberConverter {

    public CardNumber toCardNumber(final CardNumberDocument document) {
        return new CardNumber(UUID.fromString(document.getId()), document.getTokenized());
    }

    public CardNumberDocument toDocument(final CardNumber cardNumber) {
        final CardNumberDocument document = new CardNumberDocument();
        document.setId(cardNumber.getId().toString());
        document.setTokenized(cardNumber.getTokenized());
        return document;
    }

}
