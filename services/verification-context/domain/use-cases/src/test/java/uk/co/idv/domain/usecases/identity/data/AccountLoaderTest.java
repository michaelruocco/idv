package uk.co.idv.domain.usecases.identity.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.card.number.CardNumber;
import uk.co.idv.domain.entities.card.number.CreditCardNumber;
import uk.co.idv.domain.entities.card.number.DebitCardNumber;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequestMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class AccountLoaderTest {

    private final AccountLoader loader = new AccountLoader();

    @Test
    void shouldReturnEmptyListIfProvidedAliasIsNotACardNumber() {
        final Alias alias = AliasesMother.idvId();
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final Collection<Account> accounts = loader.load(request);

        assertThat(accounts).isEmpty();
    }

    @Test
    void shouldReturnEmptyListIfProvidedAliasIsACardNumberButAliasEndsInNine() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111119");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final Collection<Account> accounts = loader.load(request);

        assertThat(accounts).isEmpty();
    }

    @Test
    void shouldReturnOpenAccountWithAliasValueCreditCardNumberIfProvidedAliasIsACreditCardNumberAndValueDoesNotEndInNine() {
        final Alias alias = AliasesMother.creditCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final Collection<Account> accounts = loader.load(request);

        final Account expectedAccount = AccountMother.open(toCreditCardNumber(alias));
        assertThat(accounts).containsExactly(expectedAccount);
    }

    @Test
    void shouldReturnOpenAccountWithAliasValueDebitCardNumberIfProvidedAliasIsADebitCardNumberAndValueDoesNotEndInNine() {
        final Alias alias = AliasesMother.debitCardNumber("4929001111111111");
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.withProvidedAlias(alias);

        final Collection<Account> accounts = loader.load(request);

        final Account expectedAccount = AccountMother.open(toDebitCardNumber(alias));
        assertThat(accounts).containsExactly(expectedAccount);
    }

    private static CardNumber toCreditCardNumber(final Alias alias) {
        return new CreditCardNumber(alias.getValue());
    }

    private static CardNumber toDebitCardNumber(final Alias alias) {
        return new DebitCardNumber(alias.getValue());
    }

}
