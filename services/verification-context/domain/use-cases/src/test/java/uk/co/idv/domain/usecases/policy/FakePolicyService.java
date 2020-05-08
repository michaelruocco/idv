package uk.co.idv.domain.usecases.policy;

import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.UUID;

public class FakePolicyService<T extends Policy> implements PolicyService<T> {

    private T lastCreatedPolicy;
    private T lastUpdatedPolicy;
    private Collection<T> policiesToLoad;
    private T policyToLoad;
    private UUID lastLoadedId;

    @Override
    public void create(final T policy) {
        this.lastCreatedPolicy = policy;
    }

    @Override
    public void update(final T policy) {
        this.lastUpdatedPolicy = policy;
    }

    @Override
    public T load(final UUID id) {
        lastLoadedId = id;
        return policyToLoad;
    }

    @Override
    public T load(final PolicyRequest request) {
        return policyToLoad;
    }

    @Override
    public Collection<T> loadAll() {
        return policiesToLoad;
    }

    public T getLastCreatedPolicy() {
        return lastCreatedPolicy;
    }

    public T getLastUpdatedPolicy() {
        return lastUpdatedPolicy;
    }

    public void setPoliciesToLoad(final Collection<T> policies) {
        this.policiesToLoad = policies;
    }

    public void setPolicyToLoad(final T policy) {
        this.policyToLoad = policy;
    }

    public UUID getLastLoadedId() {
        return lastLoadedId;
    }

}
