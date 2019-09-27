package uk.co.mruoc.idv.identity.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.model.IdvId;

import java.util.Optional;

@Builder
public class DefaultIdentityService implements IdentityService {

    private final IdentityDao dao;
    private final IdGenerator idGenerator;

    @Override
    public Identity upsert(final UpsertIdentityRequest request) {
        final Optional<Identity> loadedIdentity = dao.load(request.getProvidedAlias());
        return loadedIdentity.orElseGet(() -> createNewIdentity(request));
    }

    private Identity createNewIdentity(final UpsertIdentityRequest request) {
        final IdvId idvId = new IdvId(idGenerator.generate());
        final Identity identity = new Identity(Aliases.with(idvId, request.getProvidedAlias()));
        dao.save(identity);
        return identity;
    }

}
