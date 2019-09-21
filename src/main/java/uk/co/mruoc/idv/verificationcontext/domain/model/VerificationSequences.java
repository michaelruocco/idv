package uk.co.mruoc.idv.verificationcontext.domain.model;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class VerificationSequences implements Iterable<VerificationSequence> {

    private final Collection<VerificationSequence> sequences;

    public VerificationSequences(final VerificationSequence... sequences) {
        this(Arrays.asList(sequences));
    }

    public VerificationSequences(final Collection<VerificationSequence> sequences) {
        this.sequences = sequences;
    }

    @Override
    public Iterator<VerificationSequence> iterator() {
        return sequences.iterator();
    }

    public boolean isEmpty() {
        return sequences.isEmpty();
    }

    public Duration calculateMaxDuration() {
        return sequences.stream()
                .map(VerificationSequence::getDuration)
                .sorted()
                .reduce((first, second) -> second)
                .orElseThrow(CannotCalculateMaxDurationOfEmptySequencesException::new);
    }

    public static class CannotCalculateMaxDurationOfEmptySequencesException extends RuntimeException {

        public CannotCalculateMaxDurationOfEmptySequencesException() {
            super();
        }

    }

}
