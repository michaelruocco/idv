package uk.co.idv.domain.entities.cardnumber;

import lombok.Getter;

@Getter
public class CreditCardNumber extends CardNumber {

    public CreditCardNumber(final String tokenized) {
        super(tokenized, CardType.CREDIT);
    }

}
