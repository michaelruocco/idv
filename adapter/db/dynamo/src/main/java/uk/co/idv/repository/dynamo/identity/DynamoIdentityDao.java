package uk.co.idv.repository.dynamo.identity;

import lombok.Builder;
import org.apache.commons.collections4.CollectionUtils;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.usecases.identity.IdentityDao;

import java.util.Collection;
import java.util.Optional;

@Builder
public class DynamoIdentityDao implements IdentityDao {

    private final IdentityDocumentConverter documentConverter;
    private final AliasConverter aliasConverter;
    private final IdentityRepository repository;

    @Override
    public void save(final Identity identity) {
        final Collection<IdentityDocument> updateDocuments = documentConverter.toDocuments(identity);
        final Collection<IdentityDocument> existingDocuments = repository.findByIdvId(identity.getIdvIdValue().toString());
        final Collection<IdentityDocument> documentsToDelete = CollectionUtils.subtract(existingDocuments, updateDocuments);
        repository.deleteAll(documentsToDelete);
        repository.saveAll(updateDocuments);
    }

    @Override
    public Optional<Identity> load(final Alias alias) {
        if (IdvId.isIdvId(alias)) {
            return loadByIdvId(alias);
        }
        return loadByAlias(alias);
    }

    private Optional<Identity> loadByAlias(final Alias alias) {
        final String key = aliasConverter.toString(alias);
        final Collection<IdentityDocument> documents = repository.findByAlias(key);
        if (documents.isEmpty()) {
            return Optional.empty();
        }
        if (documents.size() > 1) {
            throw new IllegalStateException(String.format("multiple documents %s found for alias %s", documents, key));
        }
        return loadByIdvId(documents.iterator().next().getIdvId());
    }

    private Optional<Identity> loadByIdvId(final Alias alias) {
        return loadByIdvId(alias.getValue());
    }

    private Optional<Identity> loadByIdvId(final String idvId) {
        final Collection<IdentityDocument> documents = repository.findByIdvId(idvId);
        return Optional.of(documentConverter.toIdentity(documents));
    }

}
