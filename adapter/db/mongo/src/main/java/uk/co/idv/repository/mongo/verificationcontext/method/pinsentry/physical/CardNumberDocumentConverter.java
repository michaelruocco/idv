package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CardType;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.CreditCardNumber;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.DebitCardNumber;

import java.util.UUID;

import static uk.co.mruoc.idv.verificationcontext.domain.model.method.CardType.CREDIT;

public class CardNumberDocumentConverter {

    public CardNumber toCardNumber(final CardNumberDocument document) {
        final CardType cardType = CardType.valueOf(document.getType());
        final UUID id = UUID.fromString(document.getId());
        if (cardType == CREDIT) {
            return new CreditCardNumber(id, document.getTokenized());
        }
        return new DebitCardNumber(id, document.getTokenized());
    }

    public CardNumberDocument toDocument(final CardNumber cardNumber) {
        final CardNumberDocument document = new CardNumberDocument();
        document.setId(cardNumber.getId().toString());
        document.setTokenized(cardNumber.getTokenized());
        document.setType(cardNumber.getType().name());
        return document;
    }



}
