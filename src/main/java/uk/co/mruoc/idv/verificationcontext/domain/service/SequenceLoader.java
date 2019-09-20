package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;

import java.util.Collection;

public interface SequenceLoader {

    Collection<VerificationSequence> loadSequences(LoadSequenceRequest request);
}
