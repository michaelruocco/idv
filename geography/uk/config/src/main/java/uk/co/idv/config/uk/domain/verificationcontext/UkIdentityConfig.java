package uk.co.idv.config.uk.domain.verificationcontext;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.DefaultIdentityService;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.identity.data.AccountLoader;
import uk.co.idv.domain.usecases.identity.data.AliasLoader;
import uk.co.idv.domain.usecases.identity.data.IdentityDataService;
import uk.co.idv.domain.usecases.identity.data.MobileDeviceLoader;
import uk.co.idv.domain.usecases.identity.data.PhoneNumberLoader;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;

@RequiredArgsConstructor
public class UkIdentityConfig {

    public AliasFactory aliasFactory() {
        return new AliasFactory();
    }

    public IdentityService identityService(final IdentityDao dao) {
        return DefaultIdentityService.builder()
                .idGenerator(new RandomIdGenerator())
                .dataService(identityDataService())
                .dao(dao)
                .build();
    }

    private IdentityDataService identityDataService() {
        return IdentityDataService.builder()
                .aliasLoader(new AliasLoader())
                .phoneNumberLoader(new PhoneNumberLoader())
                .accountLoader(new AccountLoader())
                .mobileDeviceLoader(new MobileDeviceLoader(new CurrentTimeProvider()))
                .build();
    }

}
