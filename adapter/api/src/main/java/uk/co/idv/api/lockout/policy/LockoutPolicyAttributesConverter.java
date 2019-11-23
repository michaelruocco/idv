package uk.co.idv.api.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

public interface LockoutPolicyAttributesConverter {

    boolean supports(String type);

    LockoutPolicy toPolicy(LockoutPolicyAttributes parameters);

    LockoutPolicyAttributes toParameters(LockoutPolicy policy);

}
