package uk.co.mruoc.idv.lockout.dao;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class InMemoryLockoutPolicyDao implements LockoutPolicyDao {

    private final Collection<LockoutPolicy> policies = new ArrayList<>();

    @Override
    public void save(final LockoutPolicy policy) {
        policies.add(policy);
    }

    @Override
    public Optional<LockoutPolicy> load(final LockoutRequest request) {
        return policies.stream()
                .filter(policy -> policy.appliesTo(request))
                .findFirst();
    }

    public Collection<LockoutPolicy> load() {
        return Collections.unmodifiableCollection(policies);
    }

}
