package uk.co.idv.domain.entities.identity;

public class CreditCardNumber extends CardNumber {

    public static final String TYPE = "credit-card-number";

    public CreditCardNumber(final String value) {
        super(TYPE, value);
    }

}
