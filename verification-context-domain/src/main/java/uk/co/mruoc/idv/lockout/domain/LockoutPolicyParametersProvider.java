package uk.co.mruoc.idv.lockout.domain;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;

import java.util.Collection;

public interface LockoutPolicyParametersProvider {

    Collection<LockoutPolicyParameters> getPolicies();

}
