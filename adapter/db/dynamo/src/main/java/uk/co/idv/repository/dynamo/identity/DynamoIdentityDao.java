package uk.co.idv.repository.dynamo.identity;

import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.repository.dynamo.identity.alias.AliasConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocument;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingDocumentConverter;
import uk.co.idv.repository.dynamo.identity.alias.AliasMappingRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Builder
@Slf4j
public class DynamoIdentityDao implements IdentityDao {

    private final AliasMappingDocumentConverter documentConverter;
    private final AliasConverter aliasConverter;
    private final AliasMappingRepository repository;

    @Override
    public void save(final Identity identity) {
        final Collection<AliasMappingDocument> updateDocuments = documentConverter.toDocuments(identity);
        final Collection<AliasMappingDocument> existingDocuments = getExistingDocuments(identity.getIdvIdValue());
        final Collection<AliasMappingDocument> documentsToDelete = CollectionUtils.subtract(existingDocuments, updateDocuments);
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

    private Collection<AliasMappingDocument> getExistingDocuments(final UUID idvIdValue) {
        try {
            return repository.findByIdvId(idvIdValue.toString());
        } catch (final ResourceNotFoundException e) {
            log.debug("no existing documents found for idv id {}", idvIdValue, e);
            return Collections.emptyList();
        }
    }

    private Optional<Identity> loadByAlias(final Alias alias) {
        final String key = aliasConverter.toString(alias);
        log.info("attempting to load mapping document using key {}", key);
        final Optional<AliasMappingDocument> document = repository.findById(key);
        return document.flatMap(this::loadByIdvId);
    }

    private Optional<Identity> loadByIdvId(final AliasMappingDocument document) {
        return loadByIdvId(document.getIdvId());
    }

    private Optional<Identity> loadByIdvId(final Alias alias) {
        return loadByIdvId(alias.getValue());
    }

    private Optional<Identity> loadByIdvId(final String idvId) {
        log.info("attempting to load mapping documents for idv id {}", idvId);
        final Collection<AliasMappingDocument> documents = repository.findByIdvId(idvId);
        if (documents.isEmpty()) {
            log.info("no existing documents found for idv id {}", idvId);
            return Optional.empty();
        }
        return Optional.of(documentConverter.toIdentity(documents));
    }

}
