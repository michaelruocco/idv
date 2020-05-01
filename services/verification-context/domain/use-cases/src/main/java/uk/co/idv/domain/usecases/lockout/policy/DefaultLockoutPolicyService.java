package uk.co.idv.domain.usecases.lockout.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.policy.PolicyLevel;
import uk.co.idv.domain.entities.policy.LockoutLevelConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class DefaultLockoutPolicyService implements LockoutPolicyService {

    private final LockoutPolicyDao dao;
    private final LockoutLevelConverter lockoutLevelConverter;
    private final MultipleLockoutPoliciesHandler multiplePoliciesHandler;

    public DefaultLockoutPolicyService(final LockoutPolicyDao dao) {
        this(dao, new LockoutLevelConverter(), new MultipleLockoutPoliciesHandler());
    }

    @Override
    public void create(final LockoutPolicy policy) {
        final Collection<LockoutPolicy> existingPolicies = loadPolicies(policy.getLevel());
        if (!existingPolicies.isEmpty()) {
            throw new LockoutPoliciesAlreadyExistException(existingPolicies);
        }
        dao.save(policy);
    }

    @Override
    public void update(final LockoutPolicy policy) {
        final UUID id = policy.getId();
        final Optional<LockoutPolicy> loadedPolicy = dao.load(id);
        if (loadedPolicy.isEmpty()) {
            throw new LockoutPolicyNotFoundException(id);
        }
        dao.save(policy);
    }

    @Override
    public Collection<LockoutPolicy> loadAll() {
        return dao.load();
    }

    @Override
    public LockoutPolicy load(final UUID id) {
        return dao.load(id)
                .orElseThrow(() -> new LockoutPolicyNotFoundException(id));
    }

    public LockoutPolicy load(final LockoutRequest request) {
        return loadPolicy(request)
                .orElseThrow(() -> new RequestedLockoutPolicyNotFoundException(request));
    }

    private Optional<LockoutPolicy> loadPolicy(final PolicyRequest request) {
        final List<LockoutPolicy> policies = loadPolicies(request);
        return multiplePoliciesHandler.extractPolicy(policies);
    }

    private List<LockoutPolicy> loadPolicies(final PolicyLevel level) {
        final Collection<PolicyRequest> requests = lockoutLevelConverter.toPolicyRequests(level);
        return requests.stream()
                .map(this::loadPolicies)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<LockoutPolicy> loadPolicies(final PolicyRequest request) {
        final Collection<LockoutPolicy> policies = dao.load(request);
        final List<LockoutPolicy> applicablePolicies = policies.stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
        log.info("found applicable policies {} for request {}", applicablePolicies, request);
        return applicablePolicies;
    }

}
