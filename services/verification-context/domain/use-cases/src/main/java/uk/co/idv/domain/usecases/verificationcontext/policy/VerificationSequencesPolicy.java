package uk.co.idv.domain.usecases.verificationcontext.policy;

import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.idv.domain.usecases.verificationcontext.sequence.LoadSequencesRequest;

public interface VerificationSequencesPolicy {

    boolean appliesTo(final LoadSequencesRequest request);

    VerificationSequences calculateSequences(final LoadSequencesRequest request);

}
