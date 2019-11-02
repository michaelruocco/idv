package uk.co.mruoc.idv.lockout.dao;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface LockoutPolicyDao {

    void save(final LockoutPolicy policy);

    Optional<LockoutPolicy> load(final UUID id);

    Optional<LockoutPolicy> load(final LockoutRequest request);

    Collection<LockoutPolicy> load();

}
