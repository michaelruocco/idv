package uk.co.mruoc.idv.identity.domain.model;

public class CreditCardNumber extends CardNumber {

    public static final String TYPE = "credit-card-number";

    public CreditCardNumber(final String value) {
        super(TYPE, value);
    }

}
