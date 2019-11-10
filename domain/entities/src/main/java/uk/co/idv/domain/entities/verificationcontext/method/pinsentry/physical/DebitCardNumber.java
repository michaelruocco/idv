package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DebitCardNumber extends CardNumber {

    public DebitCardNumber(final String tokenized) {
        this(UUID.randomUUID(), tokenized);
    }

    public DebitCardNumber(final UUID id, final String tokenized) {
        super(id, tokenized, CardType.DEBIT);
    }

}
