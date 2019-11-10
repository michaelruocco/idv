package uk.co.idv.domain.entities.lockout.policy;

import java.util.Collection;

public interface LockoutPolicyProvider {

    Collection<LockoutPolicy> getPolicies();

}
