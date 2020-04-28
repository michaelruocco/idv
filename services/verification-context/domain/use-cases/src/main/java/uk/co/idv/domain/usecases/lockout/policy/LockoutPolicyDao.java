package uk.co.idv.domain.usecases.lockout.policy;

import uk.co.idv.domain.entities.policy.PolicyRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface LockoutPolicyDao {

    void save(final LockoutPolicy policy);

    Optional<LockoutPolicy> load(final UUID id);

    Collection<LockoutPolicy> load(final PolicyRequest request);

    Collection<LockoutPolicy> load();

}
