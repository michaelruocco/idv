package uk.co.idv.repository.inmemory.policy;

import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.usecases.policy.PolicyDao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class InMemoryPolicyDao<T extends Policy> implements PolicyDao<T> {

    private final Map<UUID, T> policies = new HashMap<>();

    @Override
    public void save(final T policy) {
        policies.put(policy.getId(), policy);
    }

    @Override
    public Optional<T> load(final UUID id) {
        return Optional.ofNullable(policies.get(id));
    }

    @Override
    public Collection<T> load(final PolicyRequest request) {
        return policies.values().stream()
                .filter(policy -> policy.appliesTo(request))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<T> load() {
        return policies.values();
    }

}
