package uk.co.idv.domain.usecases.verificationcontext.policy;

import lombok.Builder;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicy;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyProvider;
import uk.co.idv.domain.usecases.policy.DefaultPolicyCreator;

public class VerificationPolicyCreator extends DefaultPolicyCreator<VerificationPolicy> {

    @Builder
    public VerificationPolicyCreator(final VerificationPolicyProvider policyProvider,
                                     final VerificationPolicyService policyService) {
        super(policyProvider, policyService);
    }

}
