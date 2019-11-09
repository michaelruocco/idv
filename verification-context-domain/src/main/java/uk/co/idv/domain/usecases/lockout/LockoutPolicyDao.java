package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.LockoutPolicy;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface LockoutPolicyDao {

    void save(final LockoutPolicy policy);

    Optional<LockoutPolicy> load(final UUID id);

    Optional<LockoutPolicy> load(final LockoutRequest request);

    Collection<LockoutPolicy> load();

}
