package uk.co.idv.domain.usecases.verificationcontext.sequence.policy;

import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

public interface VerificationSequencesPolicy {

    boolean appliesTo(final VerificationSequencesPolicyRequest request);

    VerificationSequences buildSequences(final VerificationSequencesPolicyRequest request);

}
