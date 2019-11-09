package uk.co.idv.domain.entities.lockout;

import java.util.Collection;

public interface LockoutPolicyParametersProvider {

    Collection<LockoutPolicyParameters> getPolicies();

}
