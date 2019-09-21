package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

public interface SequenceLoader {

    VerificationSequences loadSequences(LoadSequenceRequest request);

}
