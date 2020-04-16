package uk.co.idv.domain.usecases.identity.data;

import lombok.Builder;
import uk.co.idv.domain.usecases.identity.UpsertIdentityRequest;

@Builder
public class IdentityDataService {

    private final AliasLoader aliasLoader;
    private final PhoneNumberLoader phoneNumberLoader;
    private final AccountLoader accountLoader;

    public IdentityDataResponse load(final UpsertIdentityRequest request) {
        return IdentityDataResponse.builder()
                .aliases(aliasLoader.load(request))
                .phoneNumbers(phoneNumberLoader.load(request))
                .accounts(accountLoader.load(request))
                .build();
    }

}
