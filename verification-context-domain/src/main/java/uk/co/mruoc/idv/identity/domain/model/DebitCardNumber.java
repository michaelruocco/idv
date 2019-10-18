package uk.co.mruoc.idv.identity.domain.model;

public class DebitCardNumber extends CardNumber {

    public static final String TYPE = "debit-card-number";

    public DebitCardNumber(final String value) {
        super(TYPE, value);
    }

}
