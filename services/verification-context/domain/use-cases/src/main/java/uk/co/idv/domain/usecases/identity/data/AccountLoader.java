package uk.co.idv.domain.usecases.identity.data;

import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.OpenAccount;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CreditCardNumber;
import uk.co.idv.domain.entities.card.number.DebitCardNumber;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;

import java.util.Collection;
import java.util.Collections;

public class AccountLoader {

    public Collection<Account> load(final UpsertIdentityRequest request) {
        final Alias providedAlias = request.getProvidedAlias();
        if (providedAlias.isCardNumber() && valueEndsWithNine(providedAlias)) {
            return Collections.singleton(toAccount(providedAlias));
        }
        return Collections.emptyList();
    }

    private boolean valueEndsWithNine(final Alias alias) {
        return alias.getValue().endsWith("9");
    }

    private Account toAccount(final Alias alias) {
        final CardNumber cardNumber = toCardNumber(alias);
        return new OpenAccount(cardNumber);
    }

    private CardNumber toCardNumber(final Alias alias) {
        if (uk.co.idv.domain.entities.identity.alias.CreditCardNumber.TYPE.equals(alias.getType())) {
            return new CreditCardNumber(alias.getValue());
        }
        return new DebitCardNumber(alias.getValue());
    }

}
