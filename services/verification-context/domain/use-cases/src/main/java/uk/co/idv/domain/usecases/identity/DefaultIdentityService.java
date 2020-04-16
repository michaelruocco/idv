package uk.co.idv.domain.usecases.identity;

import lombok.Builder;
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

    @Override
    public Identity upsert(final UpsertIdentityRequest request) {
        final Optional<Identity> loadedIdentity = dao.load(request.getProvidedAlias());
        return loadedIdentity.orElseGet(() -> createNewIdentity(request));
    }

    @Override
    public Identity load(final LoadIdentityRequest request) {
        final Alias alias = request.getAlias();
        return dao.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
    }

    private Identity createNewIdentity(final UpsertIdentityRequest request) {
        final IdvId idvId = createNewIdvId();
        final IdentityDataResponse response = dataService.load(request);
        final Identity identity = Identity.builder()
                .aliases(response.getAliases().add(idvId))
                .phoneNumbers(response.getPhoneNumbers())
                .accounts(response.getAccounts())
                .build();
        dao.save(identity);
        return identity;
    }

    private IdvId createNewIdvId() {
        return new IdvId(idGenerator.generate());
    }

}
