package uk.co.idv.domain.usecases.verificationcontext.sequence;

import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

public class FakeSequenceLoader implements SequenceLoader {

    private final VerificationSequences sequences;

    private LoadSequencesRequest lastRequest;

    public FakeSequenceLoader(final VerificationSequences sequences) {
        this.sequences = sequences;
    }

    @Override
    public VerificationSequences loadSequences(final LoadSequencesRequest request) {
        this.lastRequest = request;
        return sequences;
    }

    public LoadSequencesRequest getLastRequest() {
        return lastRequest;
    }

}
