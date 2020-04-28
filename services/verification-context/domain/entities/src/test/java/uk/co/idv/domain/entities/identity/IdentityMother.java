package uk.co.idv.domain.entities.identity;

import uk.co.idv.domain.entities.card.account.Account;
import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.identity.Identity.IdentityBuilder;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumber;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumbers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class IdentityMother {

    public static Identity build() {
        return defaultBuilder().build();
    }

    public static Identity emptyData() {
        return emptyDataBuilder().build();
    }

    public static Identity withAliases(final Aliases aliases) {
        return defaultBuilder()
                .aliases(aliases)
                .build();
    }

    public static Identity withMobileDevices(final Collection<MobileDevice> devices) {
        return defaultBuilder()
                .mobileDevices(devices)
                .build();
    }

    public static Identity withAccounts(final Account... accounts) {
        return withAccounts(Arrays.asList(accounts));
    }

    public static Identity withAccounts(final Collection<Account> accounts) {
        return defaultBuilder()
                .accounts(accounts)
                .build();
    }

    public static Identity withPhoneNumbers(final PhoneNumber... phoneNumbers) {
        return withPhoneNumbers(new PhoneNumbers(phoneNumbers));
    }

    public static Identity withPhoneNumbers(final PhoneNumbers phoneNumbers) {
        return defaultBuilder()
                .phoneNumbers(phoneNumbers)
                .build();
    }

    public static IdentityBuilder emptyDataBuilder() {
        return Identity.builder()
                .aliases(AliasesMother.aliases())
                .phoneNumbers(PhoneNumberMother.empty())
                .accounts(Collections.emptyList())
                .mobileDevices(Collections.emptyList());
    }

    private static Identity.IdentityBuilder defaultBuilder() {
        return Identity.builder()
                .aliases(AliasesMother.aliases())
                .phoneNumbers(PhoneNumberMother.two())
                .accounts(AccountMother.two())
                .mobileDevices(MobileDeviceMother.two());
    }

}
