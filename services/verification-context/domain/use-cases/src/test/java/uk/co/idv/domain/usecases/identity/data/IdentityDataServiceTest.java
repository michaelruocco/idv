package uk.co.idv.domain.usecases.identity.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequestMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class IdentityDataServiceTest {

    private final AliasLoader aliasLoader = mock(AliasLoader.class);
    private final PhoneNumberLoader phoneNumberLoader = mock(PhoneNumberLoader.class);
    private final AccountLoader accountLoader = mock(AccountLoader.class);
    private final MobileApplicationEligibleLoader mobileApplicationEligibleLoader = mock(MobileApplicationEligibleLoader.class);

    private final IdentityDataService service = IdentityDataService.builder()
            .aliasLoader(aliasLoader)
            .phoneNumberLoader(phoneNumberLoader)
            .accountLoader(accountLoader)
            .mobileApplicationEligibleLoader(mobileApplicationEligibleLoader)
            .build();

    @Test
    void shouldReturnAliasesFromAliasLoader() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        final Aliases expectedAliases = AliasesMother.aliases();
        given(aliasLoader.load(request)).willReturn(expectedAliases);

        final IdentityDataResponse response = service.load(request);

        assertThat(response.getAliases()).isEqualTo(expectedAliases);
    }

    @Test
    void shouldReturnPhoneNumbersFromPhoneNumberLoader() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        final PhoneNumbers expectedPhoneNumbers = PhoneNumberMother.twoNumbers();
        given(phoneNumberLoader.load(request)).willReturn(expectedPhoneNumbers);

        final IdentityDataResponse response = service.load(request);

        assertThat(response.getPhoneNumbers()).isEqualTo(expectedPhoneNumbers);
    }

    @Test
    void shouldReturnAccountsFromAccountLoader() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        final Collection<Account> expectedAccounts = AccountMother.two();
        given(accountLoader.load(request)).willReturn(expectedAccounts);

        final IdentityDataResponse response = service.load(request);

        assertThat(response.getAccounts()).isEqualTo(expectedAccounts);
    }

    @Test
    void shouldReturnMobileApplicationEligibleFromMobileApplicationEligibleLoader() {
        final UpsertIdentityRequest request = UpsertIdentityRequestMother.build();
        given(mobileApplicationEligibleLoader.load(request)).willReturn(true);

        final IdentityDataResponse response = service.load(request);

        assertThat(response.isMobileApplicationEligible()).isTrue();
    }

}
