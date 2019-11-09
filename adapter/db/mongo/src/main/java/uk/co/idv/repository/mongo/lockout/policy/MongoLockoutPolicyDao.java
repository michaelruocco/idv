package uk.co.idv.repository.mongo.lockout.policy;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.dao.LockoutPolicyDao;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyParametersConverter;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public class MongoLockoutPolicyDao implements LockoutPolicyDao {

    private final LockoutPolicyRepository repository;
    private final LockoutPolicyDocumentConverterDelegator documentConverter;
    private final LockoutPolicyParametersConverter parametersConverter;

    @Override
    public void save(final LockoutPolicy policy) {
        final LockoutPolicyParameters parameters = policy.getParameters();
        final LockoutPolicyDocument document = documentConverter.toDocument(parameters);
        repository.save(document);
    }

    @Override
    public Optional<LockoutPolicy> load(final UUID id) {
        final Optional<LockoutPolicyDocument> document = repository.findById(id.toString());
        return document
                .map(documentConverter::toParameters)
                .map(parametersConverter::toPolicy);
    }

    @Override
    public Collection<LockoutPolicy> load() {
        final Collection<LockoutPolicyDocument> documents = repository.findAll();
        return documents.stream()
                .map(documentConverter::toParameters)
                .map(parametersConverter::toPolicy)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutRequest request) {
        final String key = LockoutPolicyDocumentKeyConverter.toKey(request);
        final Optional<LockoutPolicyDocument> document = repository.findById(key);
        return document.map(this::toPolicy);
    }

    private LockoutPolicy toPolicy(final LockoutPolicyDocument document) {
        final LockoutPolicyParameters parameters = documentConverter.toParameters(document);
        return parametersConverter.toPolicy(parameters);
    }

}
