package uk.co.idv.domain.entities.verificationcontext;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
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
                .orElse(Duration.ZERO);
    }

    public void addResultIfHasSequencesWithNextMethod(final VerificationResult result) {
        final String methodName = result.getMethodName();
        if (!hasSequencesWithNextMethod(methodName)) {
            throw new NotNextMethodInSequenceException(methodName);
        }
        addResult(result);
    }

    public VerificationSequence get(final String sequenceName) {
        return sequences.stream().filter(sequence -> sequence.getName().equals(sequenceName))
                .findFirst()
                .orElseThrow(() -> new NoSequencesFoundWithNameException(sequenceName));
    }

    public VerificationResults getResults(final String sequenceName, final String methodName) {
        final VerificationSequence sequence = get(sequenceName);
        final VerificationMethod method = sequence.getMethod(methodName);
        return method.getResults();
    }

    public boolean hasSequencesWithNextMethod(final String methodName) {
        return sequences.stream().anyMatch(sequence -> sequence.hasNextMethod(methodName));
    }

    public boolean containsCompleteMethod(String methodName) {
        return sequences.stream().anyMatch(sequence -> sequence.containsCompleteMethod(methodName));
    }

    public boolean containsCompleteSequenceContainingMethod(String methodName) {
        return sequences.stream().anyMatch(sequence -> sequence.containsMethod(methodName) && sequence.isComplete());
    }

    public Stream<VerificationSequence> stream() {
        return sequences.stream();
    }

    public VerificationMethod getNextEligibleMethod(final String methodName) {
        final Collection<VerificationMethod> methods = sequences.stream()
                .filter(sequence -> sequence.hasNextMethod(methodName))
                .map(sequence -> sequence.getMethod(methodName))
                .collect(Collectors.toSet());
        switch (methods.size()) {
            case 0:
                throw new NotNextMethodInSequenceException(methodName);
            case 1:
                return IterableUtils.get(methods, 0);
            default:
                throw new CannotDetermineWhichNextMethodToUseException(methodName);
        }
    }

    private void addResult(final VerificationResult result) {
        sequences.forEach(sequence -> sequence.addResultIfHasNextMethod(result));
    }

    public static class NotNextMethodInSequenceException extends RuntimeException {

        public NotNextMethodInSequenceException(final String methodName) {
            super(String.format("%s is not the next method in any sequences", methodName));
        }

    }

    public static class NoSequencesFoundWithNameException extends RuntimeException {

        public NoSequencesFoundWithNameException(final String sequenceName) {
            super(sequenceName);
        }

    }

    public static class CannotDetermineWhichNextMethodToUseException extends RuntimeException {

        public CannotDetermineWhichNextMethodToUseException(final String methodName) {
            super(String.format("found multple instances of next method %s with different configurations", methodName));
        }

    }

}
