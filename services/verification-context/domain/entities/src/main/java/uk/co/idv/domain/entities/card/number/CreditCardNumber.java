package uk.co.idv.domain.entities.card.number;

import lombok.Getter;

@Getter
public class CreditCardNumber extends CardNumber {

    public static final String TYPE = "credit";

    public CreditCardNumber(final String tokenized) {
        super(TYPE, tokenized);
    }

}
