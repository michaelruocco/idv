package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import java.util.UUID;

public class CreditCardNumberMother {

    public static CreditCardNumber build() {
        final UUID id = UUID.fromString("6c880ce6-0d3c-4ac7-b419-8c2dce645cfa");
        return new CreditCardNumber(id, "4929991234567890");
    }

}
