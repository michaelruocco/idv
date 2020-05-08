package uk.co.idv.domain.usecases.policy;

import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyRequest;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface PolicyDao<T extends Policy> {

    void save(final T policy);

    Optional<T> load(final UUID id);

    Collection<T> load(final PolicyRequest request);

    Collection<T> load();

}
