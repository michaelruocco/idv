package uk.co.idv.domain.usecases.verificationcontext.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.PolicyLevelConverter;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DefaultVerificationSequencesPolicyService implements VerificationSequencePolicyService {

    private final VerificationSequencePolicyDao dao;
    private final PolicyLevelConverter policyLevelConverter;
    private final MultipleVerificationSequencesPoliciesHandler multiplePoliciesHandler;

    public DefaultVerificationSequencesPolicyService(final VerificationSequencePolicyDao dao) {
        this(dao, new PolicyLevelConverter(), new MultipleVerificationSequencesPoliciesHandler());
    }

    @Override
    public void create(final VerificationSequencesPolicy policy) {
        final Collection<VerificationSequencesPolicy> existingPolicies = loadPolicies(policy.getLevel());
        if (!existingPolicies.isEmpty()) {
            throw new VerificationSequencesPolicyAlreadyExistsException(existingPolicies);
        }
        dao.save(policy);
    }

    @Override
    public void update(final VerificationSequencesPolicy policy) {
        final UUID id = policy.getId();
        final Optional<VerificationSequencesPolicy> loadedPolicy = dao.load(id);
        if (loadedPolicy.isEmpty()) {
            throw new VerificationSequencesPolicyNotFoundException(id);
        }
        dao.save(policy);
    }

    @Override
    public Collection<VerificationSequencesPolicy> loadAll() {
        return dao.load();
    }

    @Override
    public VerificationSequencesPolicy load(final UUID id) {
        return dao.load(id)
                .orElseThrow(() -> new VerificationSequencesPolicyNotFoundException(id));
    }

    @Override
    public VerificationSequencesPolicy load(final PolicyRequest request) {
        return loadPolicy(request)
                .orElseThrow(() -> new RequestedVerificationSequencesPolicyNotFoundException(request));
    }

    private Optional<VerificationSequencesPolicy> loadPolicy(final PolicyRequest request) {
        final List<VerificationSequencesPolicy> policies = loadPolicies(request);
        return multiplePoliciesHandler.extractPolicy(policies);
    }

    private List<VerificationSequencesPolicy> loadPolicies(final PolicyLevel level) {
        final Collection<PolicyRequest> requests = policyLevelConverter.toPolicyRequests(level);
        return requests.stream()
                .map(this::loadPolicies)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<VerificationSequencesPolicy> loadPolicies(final PolicyRequest request) {
        final Collection<VerificationSequencesPolicy> policies = dao.load(request);
        final List<VerificationSequencesPolicy> applicablePolicies = policies.stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
        log.info("found applicable policies {} for request {}", applicablePolicies, request);
        return applicablePolicies;
    }

}
