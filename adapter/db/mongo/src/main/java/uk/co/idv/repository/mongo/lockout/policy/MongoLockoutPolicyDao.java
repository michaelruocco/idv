package uk.co.idv.repository.mongo.lockout.policy;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public class MongoLockoutPolicyDao implements LockoutPolicyDao {

    private final LockoutPolicyRepository repository;
    private final LockoutPolicyDocumentConverterDelegator documentConverter;
    private final LockoutPolicyDocumentKeyConverter keyConverter;

    @Override
    public void save(final LockoutPolicy policy) {
        final LockoutPolicyDocument document = documentConverter.toDocument(policy);
        repository.save(document);
    }

    @Override
    public Optional<LockoutPolicy> load(final UUID id) {
        final Optional<LockoutPolicyDocument> document = repository.findById(id.toString());
        return document.map(documentConverter::toPolicy);
    }

    @Override
    public Collection<LockoutPolicy> load() {
        final Collection<LockoutPolicyDocument> documents = repository.findAll();
        return documents.stream()
                .map(documentConverter::toPolicy)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutRequest request) {
        final Collection<String> keys = keyConverter.toKeys(request);
        for (String key : keys) {
            final Optional<LockoutPolicyDocument> document = repository.findById(key);
            if (document.isPresent()) {
                return document.map(this::toPolicy);
            }
        }
        return Optional.empty();
    }

    private LockoutPolicy toPolicy(final LockoutPolicyDocument document) {
        return documentConverter.toPolicy(document);
    }

}
