package uk.co.idv.domain.entities.identity;

import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.identity.Identity.IdentityBuilder;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.domain.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class IdentityMother {

    public static Identity build() {
        return withAliases(AliasesMother.aliases());
    }

    public static Identity withAliases(final Aliases aliases) {
        return Identity.builder()
                .aliases(aliases)
                .phoneNumbers(PhoneNumberMother.two())
                .accounts(AccountMother.two())
                .mobileDevices(MobileDeviceMother.two())
                .build();
    }

    public static Identity emptyData() {
        return emptyDataBuilder().build();
    }

    public static IdentityBuilder emptyDataBuilder() {
        return Identity.builder()
                .aliases(AliasesMother.aliases())
                .phoneNumbers(PhoneNumberMother.empty())
                .accounts(Collections.emptyList())
                .mobileDevices(Collections.emptyList());
    }

}
