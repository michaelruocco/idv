package uk.co.idv.domain.entities.verificationcontext.sequence.policy;

import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;

public interface VerificationSequencesPolicy {

    boolean appliesTo(final VerificationSequencesPolicyRequest request);

    VerificationSequences buildSequences(final VerificationSequencesPolicyRequest request);

}
