package uk.co.idv.domain.usecases.verificationcontext.policy;

import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicy;
import uk.co.idv.domain.entities.verificationcontext.sequence.policy.VerificationSequencesPolicyProvider;
import uk.co.idv.domain.usecases.policy.InitialPolicyCreator;


public class InitialVerificationSequencePolicyCreator extends InitialPolicyCreator<VerificationSequencesPolicy> {

    public InitialVerificationSequencePolicyCreator(final VerificationSequencesPolicyProvider policyProvider,
                                                    final VerificationSequencePolicyService policyService) {
        super(policyProvider, policyService);
    }

}
