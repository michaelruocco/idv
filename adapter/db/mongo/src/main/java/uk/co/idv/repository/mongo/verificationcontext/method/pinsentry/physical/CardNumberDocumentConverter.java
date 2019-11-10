package uk.co.idv.repository.mongo.verificationcontext.method.pinsentry.physical;

import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardType;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CreditCardNumber;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.DebitCardNumber;

import java.util.UUID;

import static uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.CardType.CREDIT;

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
