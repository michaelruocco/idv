package uk.co.mruoc.idv.mongo.identity.dao;

import lombok.Builder;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.Identity;

import java.util.Collection;
import java.util.Optional;

@Builder
public class MongoIdentityDao implements IdentityDao {

    private final IdentityRepository repository;
    private final IdentityDocumentConverter converter;

    @Override
    public void save(final Identity identity) {
        final IdentityDocument document = converter.toDocument(identity);
        repository.insert(document);
    }

    @Override
    public Optional<Identity> load(final Alias alias) {
        final Collection<IdentityDocument> documents = repository.findByAliasesTypeAndAliasesValue(alias.getType(), alias.getValue());
        if (documents.isEmpty()) {
            return Optional.empty();
        }
        if (documents.size() == 1) {
            final Identity identity = converter.toIdentity(documents.iterator().next());
            return Optional.of(identity);
        }
        throw new MultipleIdentitiesFoundException(alias); // should never happen!
    }

    public static class MultipleIdentitiesFoundException extends RuntimeException {

        private final Alias alias;

        private MultipleIdentitiesFoundException(final Alias alias) {
            super(String.format("found multiple identities for alias %s", alias));
            this.alias = alias;
        }

        public Alias getAlias() {
            return alias;
        }

    }

}
