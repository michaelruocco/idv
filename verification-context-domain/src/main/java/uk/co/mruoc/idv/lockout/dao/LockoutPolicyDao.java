package uk.co.mruoc.idv.lockout.dao;

import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicy;
import uk.co.mruoc.idv.lockout.domain.service.LockoutRequest;

import java.util.Optional;

public interface LockoutPolicyDao {

    void save(final LockoutPolicy policy);

    Optional<LockoutPolicy> load(final LockoutRequest request);

}
