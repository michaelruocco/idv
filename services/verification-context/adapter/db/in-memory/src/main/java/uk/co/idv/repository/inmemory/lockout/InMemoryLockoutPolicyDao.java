package uk.co.idv.repository.inmemory.lockout;

import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.repository.inmemory.policy.InMemoryPolicyDao;

public class InMemoryLockoutPolicyDao extends InMemoryPolicyDao<LockoutPolicy> implements LockoutPolicyDao {

    // intentionally blank

}
