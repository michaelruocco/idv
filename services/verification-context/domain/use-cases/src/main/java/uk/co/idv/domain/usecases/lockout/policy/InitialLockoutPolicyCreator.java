package uk.co.idv.domain.usecases.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.policy.InitialPolicyCreator;


public class InitialLockoutPolicyCreator extends InitialPolicyCreator<LockoutPolicy> {

    public InitialLockoutPolicyCreator(final LockoutPolicyProvider policyProvider,
                                       final LockoutPolicyService policyService) {
        super(policyProvider, policyService);
    }

}
