package uk.co.idv.domain.entities.identity.alias;

public class DebitCardNumber extends CardNumber {

    public static final String TYPE = "debit-card-number";

    public DebitCardNumber(final String value) {
        super(TYPE, value);
    }

}
