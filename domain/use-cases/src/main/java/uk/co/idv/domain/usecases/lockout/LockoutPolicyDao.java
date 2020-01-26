package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface LockoutPolicyDao {

    void save(final LockoutPolicy policy);

    Optional<LockoutPolicy> load(final UUID id);

    Collection<LockoutPolicy> load(final LockoutPolicyRequest request);

    Collection<LockoutPolicy> load();

}
