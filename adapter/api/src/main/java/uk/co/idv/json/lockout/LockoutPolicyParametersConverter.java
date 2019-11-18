package uk.co.idv.json.lockout;

import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

public interface LockoutPolicyParametersConverter {

    boolean supports(String type);

    LockoutPolicy toPolicy(LockoutPolicyParameters parameters);

    LockoutPolicyParameters toParameters(LockoutPolicy policy);

}
