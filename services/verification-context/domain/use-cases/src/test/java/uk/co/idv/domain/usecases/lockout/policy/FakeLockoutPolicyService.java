package uk.co.idv.domain.usecases.lockout.policy;

import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.UUID;

public class FakeLockoutPolicyService implements LockoutPolicyService {

    private LockoutPolicy lastCreatedPolicy;
    private LockoutPolicy lastUpdatedPolicy;
    private Collection<LockoutPolicy> policiesToLoad;
    private LockoutPolicy policyToLoad;
    private UUID lastLoadedId;

    @Override
    public void create(final LockoutPolicy policy) {
        this.lastCreatedPolicy = policy;
    }

    @Override
    public void update(final LockoutPolicy policy) {
        this.lastUpdatedPolicy = policy;
    }

    @Override
    public LockoutPolicy load(final UUID id) {
        lastLoadedId = id;
        return policyToLoad;
    }

    @Override
    public LockoutPolicy load(final LockoutRequest request) {
        return policyToLoad;
    }

    @Override
    public Collection<LockoutPolicy> loadAll() {
        return policiesToLoad;
    }

    public LockoutPolicy getLastCreatedPolicy() {
        return lastCreatedPolicy;
    }

    public LockoutPolicy getLastUpdatedPolicy() {
        return lastUpdatedPolicy;
    }

    public void setPoliciesToLoad(final Collection<LockoutPolicy> policies) {
        this.policiesToLoad = policies;
    }

    public void setPolicyToLoad(final LockoutPolicy policy) {
        this.policyToLoad = policy;
    }

    public UUID getLastLoadedId() {
        return lastLoadedId;
    }

}
