package uk.co.idv.repository.inmemory.lockout;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class InMemoryLockoutPolicyDao implements LockoutPolicyDao {

    private final Map<UUID, LockoutPolicy> policies = new HashMap<>();

    @Override
    public void save(final LockoutPolicy policy) {
        policies.put(policy.getId(), policy);
    }

    @Override
    public Optional<LockoutPolicy> load(final UUID id) {
        return Optional.ofNullable(policies.get(id));
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutRequest request) {
        return policies.values().stream()
                .filter(policy -> policy.appliesTo(request))
                .findFirst();
    }

    @Override
    public Collection<LockoutPolicy> load() {
        return policies.values();
    }

}
