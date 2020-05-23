package uk.co.idv.domain.usecases.verificationcontext.policy;

import lombok.Builder;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyProvider;
import uk.co.idv.domain.usecases.policy.InitialPolicyCreator;

public class InitialVerificationPolicyCreator extends InitialPolicyCreator<VerificationPolicy> {

    @Builder
    public InitialVerificationPolicyCreator(final VerificationPolicyProvider policyProvider,
                                            final VerificationPolicyService policyService) {
        super(policyProvider, policyService);
    }

}
