package uk.co.idv.domain.usecases.verificationcontext.sequence;

import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;

public interface SequenceLoader {

    VerificationSequences loadSequences(LoadSequencesRequest request);

}
