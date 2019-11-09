package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

public interface SequenceLoader {

    VerificationSequences loadSequences(LoadSequencesRequest request);

}
