package uk.co.mruoc.idv.identity.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.Identity;
import uk.co.mruoc.idv.identity.domain.model.IdvId;

@Builder
public class DefaultIdentityService implements IdentityService {

    private final IdGenerator idGenerator;

    @Override
    public Identity upsert(final UpsertIdentityRequest request) {
        final IdvId idvId = new IdvId(idGenerator.generate());
        return new Identity(Aliases.with(idvId, request.getProvidedAlias()));
    }

}
