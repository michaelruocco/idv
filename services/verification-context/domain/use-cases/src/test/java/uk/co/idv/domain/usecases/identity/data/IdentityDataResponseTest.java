package uk.co.idv.domain.usecases.identity.data;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class IdentityDataResponseTest {

    @Test
    public void shouldReturnAliases() {
        final Aliases aliases = AliasesMother.aliases();

        final IdentityDataResponse response = IdentityDataResponse.builder()
                .aliases(aliases)
                .build();

        assertThat(response.getAliases()).isEqualTo(aliases);
    }

    @Test
    public void shouldReturnPhoneNumbers() {
        final PhoneNumbers numbers = PhoneNumberMother.two();

        final IdentityDataResponse response = IdentityDataResponse.builder()
                .phoneNumbers(numbers)
                .build();

        assertThat(response.getPhoneNumbers()).isEqualTo(numbers);
    }

    @Test
    public void shouldReturnAccounts() {
        final Collection<Account> accounts = AccountMother.two();

        final IdentityDataResponse response = IdentityDataResponse.builder()
                .accounts(accounts)
                .build();

        assertThat(response.getAccounts()).isEqualTo(accounts);
    }

    @Test
    public void shouldReturnMobileDevices() {
        final Collection<MobileDevice> devices = Collections.emptyList();

        final IdentityDataResponse response = IdentityDataResponse.builder()
                .mobileDevices(devices)
                .build();

        assertThat(response.getMobileDevices()).isEqualTo(devices);
    }

}
