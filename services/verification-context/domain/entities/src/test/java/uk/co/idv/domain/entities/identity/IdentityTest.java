package uk.co.idv.domain.entities.identity;

import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityTest {

    @Test
    void shouldReturnAliases() {
        final Aliases aliases = Aliases.empty();

        final Identity identity = Identity.builder()
                .aliases(aliases)
                .build();

        assertThat(identity.getAliases()).isEqualTo(aliases);
    }

    @Test
    void shouldReturnIdvIdAliasValue() {
        final IdvId idvId = AliasesMother.idvId();

        final Identity identity = Identity.builder()
                .aliases(Aliases.with(idvId))
                .build();

        assertThat(identity.getIdvIdValue()).isEqualTo(idvId.getValueAsUuid());
    }

    @Test
    void shouldReturnTrueIfHasAlias() {
        final IdvId idvId = AliasesMother.idvId();

        final Identity identity = Identity.builder()
                .aliases(Aliases.with(idvId))
                .build();

        assertThat(identity.hasAlias(idvId)).isTrue();
    }

    @Test
    void shouldReturnFalseIfDoesNotHaveAlias() {
        final IdvId idvId = AliasesMother.idvId();

        final Identity identity = Identity.builder()
                .aliases(Aliases.with(idvId))
                .build();

        final Alias creditCardNumber = AliasesMother.creditCardNumber();
        assertThat(identity.hasAlias(creditCardNumber)).isFalse();
    }

    @Test
    void shouldReturnPhoneNumbers() {
        final PhoneNumbers numbers = PhoneNumberMother.two();

        final Identity identity = Identity.builder()
                .phoneNumbers(numbers)
                .build();

        assertThat(identity.getPhoneNumbers()).isEqualTo(numbers);
    }

    @Test
    void shouldReturnMobileNumbers() {
        final PhoneNumbers numbers = PhoneNumberMother.two();

        final Identity identity = Identity.builder()
                .phoneNumbers(numbers)
                .build();

        assertThat(identity.getMobilePhoneNumbers()).containsExactly(PhoneNumberMother.mobile());
    }

    @Test
    void shouldReturnAccounts() {
        final Collection<Account> accounts = AccountMother.two();

        final Identity identity = Identity.builder()
                .accounts(accounts)
                .build();

        assertThat(identity.getAccounts()).isEqualTo(accounts);
    }

    @Test
    void shouldReturnMobileDevices() {
        final Collection<MobileDevice> devices = MobileDeviceMother.oneTrusted();

        final Identity identity = Identity.builder()
                .mobileDevices(devices)
                .build();

        assertThat(identity.getMobileDevices()).isEqualTo(devices);
    }

}
