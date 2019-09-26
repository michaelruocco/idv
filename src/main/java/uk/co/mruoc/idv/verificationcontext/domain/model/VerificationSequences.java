package uk.co.mruoc.idv.verificationcontext.domain.model;

import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class VerificationSequences implements Iterable<VerificationSequence> {

    private final List<VerificationSequence> sequences;

    public VerificationSequences(final VerificationSequence... sequences) {
        this(Arrays.asList(sequences));
    }

    public VerificationSequences(final Collection<VerificationSequence> sequences) {
        this.sequences = new ArrayList<>(sequences);
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

    public VerificationSequences addResultIfHasSequencesWithNextMethod(final VerificationResult result) {
        final String methodName = result.getMethodName();
        if (!hasEligibleSequencesWithNextMethod(methodName)) {
            throw new NoSequencesFoundWithNextMethodEligibleException(methodName);
        }
        return addResult(result);
    }

    public VerificationSequence get(final int index) {
        return sequences.get(index);
    }

    public VerificationSequence get(final String sequenceName) {
        return sequences.stream().filter(sequence -> sequence.getName().equals(sequenceName))
                .findFirst()
                .orElseThrow(() -> new NoSequencesFoundWithName(sequenceName));
    }

    public VerificationResults getResults(final String sequenceName, final String methodName) {
        final VerificationSequence sequence = get(sequenceName);
        final VerificationMethod method = sequence.getMethod(methodName);
        return method.getResults();
    }

    private VerificationSequences addResult(final VerificationResult result) {
        final Collection<VerificationSequence> updatedSequences = sequences.stream()
                .map(sequence -> sequence.addResultIfHasNextMethod(result))
                .collect(Collectors.toList());
        return new VerificationSequences(updatedSequences);
    }


    private boolean hasEligibleSequencesWithNextMethod(final String methodName) {
        return sequences.stream()
                .filter(VerificationSequence::isEligible)
                .anyMatch(sequence -> sequence.hasNextMethod(methodName));
    }

    public static class CannotCalculateMaxDurationOfEmptySequencesException extends RuntimeException {

        public CannotCalculateMaxDurationOfEmptySequencesException() {
            super();
        }

    }

    public static class NoSequencesFoundWithNextMethodEligibleException extends RuntimeException {

        public NoSequencesFoundWithNextMethodEligibleException(final String methodName) {
            super(methodName);
        }

    }

    public static class NoSequencesFoundWithName extends RuntimeException {

        public NoSequencesFoundWithName(final String sequenceName) {
            super(sequenceName);
        }

    }

}
