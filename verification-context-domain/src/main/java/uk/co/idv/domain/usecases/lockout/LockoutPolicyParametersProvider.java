package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.LockoutPolicyParameters;

import java.util.Collection;

public interface LockoutPolicyParametersProvider {

    Collection<LockoutPolicyParameters> getPolicies();

}
