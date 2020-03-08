package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;

import lombok.Getter;

@Getter
public class DebitCardNumber extends CardNumber {

    public DebitCardNumber(final String tokenized) {
        super(tokenized, CardType.DEBIT);
    }

}
