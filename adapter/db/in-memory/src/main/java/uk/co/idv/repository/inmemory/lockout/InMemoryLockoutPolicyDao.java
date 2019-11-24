package uk.co.idv.repository.inmemory.lockout;

import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.usecases.lockout.MultipleLockoutPoliciesHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryLockoutPolicyDao implements LockoutPolicyDao {

    private final Map<UUID, LockoutPolicy> policies = new HashMap<>();
    private final MultipleLockoutPoliciesHandler multiplePoliciesHandler;

    public InMemoryLockoutPolicyDao() {
        this(new MultipleLockoutPoliciesHandler());
    }

    public InMemoryLockoutPolicyDao(final MultipleLockoutPoliciesHandler multiplePoliciesHandler) {
        this.multiplePoliciesHandler = multiplePoliciesHandler;
    }

    @Override
    public void save(final LockoutPolicy policy) {
        policies.put(policy.getId(), policy);
    }

    @Override
    public Optional<LockoutPolicy> load(final UUID id) {
        return Optional.ofNullable(policies.get(id));
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutPolicyRequest request) {
        final List<LockoutPolicy> applicablePolicies = policies.values().stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
        return multiplePoliciesHandler.extractPolicy(applicablePolicies);
    }

    @Override
    public Collection<LockoutPolicy> load() {
        return policies.values();
    }

}
