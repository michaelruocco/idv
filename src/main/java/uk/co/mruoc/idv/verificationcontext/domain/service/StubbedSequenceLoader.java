package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

public class StubbedSequenceLoader implements SequenceLoader {

    @Override
    public VerificationSequences loadSequences(final LoadSequenceRequest request) {
        return new FakeVerificationSequences();
    }

}
