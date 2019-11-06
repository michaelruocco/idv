package uk.co.mruoc.idv.identity.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.CreditCardNumber;
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

    @Override
    public Identity load(final LoadIdentityRequest request) {
        final Alias alias = request.getAlias();
        return dao.load(alias).orElseThrow(() -> new IdentityNotFoundException(alias));
    }

    private Identity createNewIdentity(final UpsertIdentityRequest request) {
        final Aliases aliases = buildAliases(request.getProvidedAlias());
        final Identity identity = new Identity(aliases);
        dao.save(identity);
        return identity;
    }

    private Aliases buildAliases(final Alias providedAlias) {
        final IdvId idvId = new IdvId(idGenerator.generate());
        if (shouldCreateAdditionalAlias(providedAlias)) {
            return Aliases.with(idvId, providedAlias, createAdditionalAlias(providedAlias));
        }
        return Aliases.with(idvId, providedAlias);
    }

    private boolean shouldCreateAdditionalAlias(final Alias providedAlias) {
        return providedAlias.getValue().endsWith("2");
    }

    private Alias createAdditionalAlias(final Alias providedAlias) {
        final String value = Long.toString(Long.parseLong(providedAlias.getValue()) + 1);
        return new CreditCardNumber(value);
    }

}
