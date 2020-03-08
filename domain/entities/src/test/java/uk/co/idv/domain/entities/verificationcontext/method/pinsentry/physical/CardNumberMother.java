package uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical;



import java.util.Collection;
import java.util.Collections;

public class CardNumberMother {

    private CardNumberMother() {
        // utility class
    }

    public static Collection<CardNumber> oneCredit() {
        return Collections.singleton(credit());
    }

    public static CardNumber credit() {
        return new CreditCardNumber("4929991234567890");
    }

    public static CardNumber debit() {
        return new DebitCardNumber("4929112222222222");
    }

}
