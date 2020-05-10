package uk.co.idv.domain.usecases.identity;

import lombok.Builder;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.usecases.identity.data.IdentityDataResponse;
import uk.co.idv.domain.usecases.identity.data.IdentityDataService;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.IdvId;

import java.util.Optional;

@Builder
public class DefaultIdentityService implements IdentityService {

    private final IdGenerator idGenerator;
    private final IdentityDataService dataService;
    private final IdentityDao dao;

    //TODO need to ensure data is persisted correctly along with aliases
    @Override
    public Identity upsert(final UpsertIdentityRequest request) {
        final Optional<Identity> loadedIdentity = dao.load(request.getProvidedAlias());
        if (loadedIdentity.isPresent()) {
            final Aliases loadedAliases = loadedIdentity.get().getAliases();
            final IdentityDataResponse data = dataService.load(request);
            return Identity.builder()
                    .aliases(loadedAliases.add(data.getAliases()))
                    .phoneNumbers(data.getPhoneNumbers())
                    .accounts(data.getAccounts())
                    .mobileDevices(data.getMobileDevices())
                    .build();
        }
        return createNewIdentity(request);
    }

    @Override
    public Identity load(final LoadIdentityRequest request) {
        final Alias alias = request.getAlias();
        return dao.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
    }

    private Identity createNewIdentity(final UpsertIdentityRequest request) {
        final IdvId idvId = createNewIdvId();
        final IdentityDataResponse data = dataService.load(request);
        final Identity identity = Identity.builder()
                .aliases(data.getAliases().add(idvId))
                .phoneNumbers(data.getPhoneNumbers())
                .accounts(data.getAccounts())
                .mobileDevices(data.getMobileDevices())
                .build();
        dao.save(identity);
        return identity;
    }

    private IdvId createNewIdvId() {
        return new IdvId(idGenerator.generate());
    }

}
