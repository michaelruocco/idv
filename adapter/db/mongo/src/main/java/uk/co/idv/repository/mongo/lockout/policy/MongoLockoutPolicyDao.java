package uk.co.idv.repository.mongo.lockout.policy;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.usecases.lockout.MultipleLockoutPoliciesHandler;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
@Slf4j
public class MongoLockoutPolicyDao implements LockoutPolicyDao {

    private final LockoutPolicyRepository repository;
    private final LockoutPolicyDocumentConverterDelegator documentConverter;
    private final MultipleLockoutPoliciesHandler multiplePoliciesHandler;

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
        return documentConverter.toPolicies(documents);
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutRequest request) {
        final Collection<LockoutPolicyDocument> documents = repository.findByChannelId(request.getChannelId());
        final List<LockoutPolicy> applicablePolicies = documentConverter.toPolicies(documents)
                .stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
        log.info("found applicable policies {} for request {}", applicablePolicies, request);
        return multiplePoliciesHandler.extractPolicy(applicablePolicies);
    }

}
