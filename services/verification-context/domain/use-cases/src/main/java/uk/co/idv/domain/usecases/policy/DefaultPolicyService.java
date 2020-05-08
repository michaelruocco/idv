package uk.co.idv.domain.usecases.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.policy.PolicyLevelConverter;
import uk.co.idv.domain.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DefaultPolicyService<T extends Policy> implements PolicyService<T> {

    private final PolicyDao<T> dao;
    private final MultiplePoliciesHandler<T> multiplePoliciesHandler;
    private final PolicyLevelConverter policyLevelConverter;

    public DefaultPolicyService(final PolicyDao<T> dao, final MultiplePoliciesHandler<T> multiplePoliciesHandler) {
        this(dao, multiplePoliciesHandler, new PolicyLevelConverter());
    }

    @Override
    public void create(final T policy) {
        final Collection<T> existingPolicies = loadPolicies(policy.getLevel());
        if (!existingPolicies.isEmpty()) {
            final Collection<UUID> ids = extractIds(existingPolicies);
            throw new PoliciesAlreadyExistException(ids);
        }
        dao.save(policy);
    }

    @Override
    public void update(final T policy) {
        final UUID id = policy.getId();
        final Optional<T> loadedPolicy = dao.load(id);
        if (loadedPolicy.isEmpty()) {
            throw new PolicyNotFoundException(id);
        }
        dao.save(policy);
    }

    @Override
    public Collection<T> loadAll() {
        return dao.load();
    }

    @Override
    public T load(final UUID id) {
        return dao.load(id)
                .orElseThrow(() -> new PolicyNotFoundException(id));
    }

    @Override
    public T load(final PolicyRequest request) {
        return loadPolicy(request)
                .orElseThrow(() -> new RequestedPolicyNotFoundException(request));
    }

    private Optional<T> loadPolicy(final PolicyRequest request) {
        final List<T> policies = loadPolicies(request);
        return multiplePoliciesHandler.extractPolicy(policies);
    }

    private List<T> loadPolicies(final PolicyLevel level) {
        final Collection<PolicyRequest> requests = policyLevelConverter.toPolicyRequests(level);
        return requests.stream()
                .map(this::loadPolicies)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<T> loadPolicies(final PolicyRequest request) {
        final Collection<T> policies = dao.load(request);
        final List<T> applicablePolicies = policies.stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
        log.info("found applicable policies {} for request {}", applicablePolicies, request);
        return applicablePolicies;
    }

    private Collection<UUID> extractIds(final Collection<T> policies) {
        return policies.stream()
                .map(Policy::getId)
                .collect(Collectors.toList());
    }

}
