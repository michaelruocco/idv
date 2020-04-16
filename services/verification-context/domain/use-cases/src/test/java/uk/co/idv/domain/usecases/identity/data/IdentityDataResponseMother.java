package uk.co.idv.domain.usecases.identity.data;

import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.phonenumber.PhoneNumberMother;

import java.util.Collections;

public class IdentityDataResponseMother {

    public static IdentityDataResponse empty() {
        return IdentityDataResponse.builder()
                .aliases(Aliases.empty())
                .phoneNumbers(PhoneNumberMother.empty())
                .accounts(Collections.emptyList())
                .build();
    }

    public static IdentityDataResponse withAlias(final Alias alias) {
        return IdentityDataResponse.builder()
                .aliases(Aliases.with(alias))
                .phoneNumbers(PhoneNumberMother.empty())
                .accounts(Collections.emptyList())
                .build();
    }

}
