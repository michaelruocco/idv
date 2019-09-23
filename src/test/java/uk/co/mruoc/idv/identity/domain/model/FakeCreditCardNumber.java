package uk.co.mruoc.idv.identity.domain.model;

public class FakeCreditCardNumber extends CreditCardNumber {

    public FakeCreditCardNumber() {
        this("4929001111111111");
    }

    public FakeCreditCardNumber(final String value) {
        super(value);
    }

}
