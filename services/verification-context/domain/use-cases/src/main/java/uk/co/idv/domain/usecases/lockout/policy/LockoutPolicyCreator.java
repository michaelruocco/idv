package uk.co.idv.domain.usecases.lockout.policy;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.policy.DefaultPolicyCreator;


public class LockoutPolicyCreator extends DefaultPolicyCreator<LockoutPolicy> {

    @Builder
    public LockoutPolicyCreator(final LockoutPolicyProvider policyProvider,
                                final LockoutPolicyService policyService) {
        super(policyProvider, policyService);
    }

}
