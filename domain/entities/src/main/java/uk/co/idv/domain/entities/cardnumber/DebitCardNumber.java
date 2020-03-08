package uk.co.idv.domain.entities.cardnumber;

import lombok.Getter;

@Getter
public class DebitCardNumber extends CardNumber {

    public DebitCardNumber(final String tokenized) {
        super(tokenized, CardType.DEBIT);
    }

}
