package uk.co.idv.domain.entities.verificationcontext.policy;

import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;

public interface VerificationPolicy extends Policy {

    VerificationSequences buildSequences(final VerificationPolicyRequest request);

}
