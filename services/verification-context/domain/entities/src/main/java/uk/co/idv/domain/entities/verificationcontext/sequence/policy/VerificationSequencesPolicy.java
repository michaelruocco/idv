package uk.co.idv.domain.entities.verificationcontext.sequence.policy;

import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;

public interface VerificationSequencesPolicy extends Policy {

    VerificationSequences buildSequences(final VerificationSequencesPolicyRequest request);

}
