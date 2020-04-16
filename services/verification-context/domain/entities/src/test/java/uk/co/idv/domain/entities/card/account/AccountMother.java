package uk.co.idv.domain.entities.card.account;

import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CardNumberMother;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class AccountMother {

    private AccountMother() {
        // utility class
    }

    public static Collection<Account> oneOpen() {
        return Collections.singleton(open());
    }

    public static Collection<Account> two() {
        return Arrays.asList(open(), closed());
    }

    public static Account open() {
        return open(CardNumberMother.credit());
    }

    public static Account open(final CardNumber... cardNumber) {
        return new OpenAccount(cardNumber);
    }

    public static Account closed() {
        return new ClosedAccount(CardNumberMother.oneDebit());
    }

}
