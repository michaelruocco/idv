package uk.co.idv.domain.entities.card.number;

import lombok.Getter;

@Getter
public class DebitCardNumber extends CardNumber {

    public static final String TYPE = "debit";

    public DebitCardNumber(final String tokenized) {
        super(TYPE, tokenized);
    }

}
