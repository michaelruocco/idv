package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;

public class FakeSequenceLoader implements SequenceLoader {

    private final VerificationSequences sequences;

    private LoadSequenceRequest lastRequest;

    public FakeSequenceLoader(final VerificationSequences sequences) {
        this.sequences = sequences;
    }

    @Override
    public VerificationSequences loadSequences(final LoadSequenceRequest request) {
        this.lastRequest = request;
        return sequences;
    }

    public LoadSequenceRequest getLastRequest() {
        return lastRequest;
    }

}
