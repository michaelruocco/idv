package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.util.UUID;

public class CardNumberMother {

    private CardNumberMother() {
        // utility class
    }

    public static CardNumber credit() {
        return new CreditCardNumber(
                UUID.fromString("b10b3852-2574-4da0-8129-e869ab5f8bb9"),
                "4929991234567890"
        );
    }

    public static CardNumber debit() {
        return new DebitCardNumber(
                UUID.fromString("9e2bdfb6-4a00-48c4-8af7-d46c26e0cd01"),
                "4929112222222222"
        );
    }

}
