package uk.co.idv.domain.entities.identity;

import uk.co.idv.domain.entities.card.account.AccountMother;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;

import java.util.Collections;

public class IdentityMother {

    public static Identity withAliases(final Aliases aliases) {
        return Identity.builder()
                .aliases(aliases)
                .phoneNumbers(PhoneNumberMother.twoNumbers())
                .accounts(AccountMother.two())
                .build();
    }

    public static Identity build() {
        return Identity.builder()
                .aliases(AliasesMother.aliases())
                .phoneNumbers(PhoneNumberMother.twoNumbers())
                .accounts(AccountMother.two())
                .build();
    }

    public static Identity emptyData() {
        return emptyData(AliasesMother.aliases());
    }

    public static Identity emptyData(final Aliases aliases) {
        return Identity.builder()
                .aliases(aliases)
                .phoneNumbers(PhoneNumberMother.empty())
                .accounts(Collections.emptyList())
                .build();
    }

}
