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

    public VerificationSequences addResultIfHasSequencesWithNextMethod(final VerificationResult result) {
        final String methodName = result.getMethodName();
        if (!hasSequencesWithNextMethod(methodName)) {
            throw new NotNextMethodInSequenceException(methodName);
        }
        return addResult(result);
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

    //TODO test this method
    public VerificationMethod getNextEligibleMethod(final String methodName) {
        final Collection<VerificationSequence> methodSequences = sequences.stream()
                .filter(sequence -> sequence.hasNextMethod(methodName))
                .collect(Collectors.toList());
        if (methodSequences.isEmpty()) {
            throw new NotNextMethodInSequenceException(methodName);
        }

        if (methodSequences.size() == 1) {
            return IterableUtils.get(methodSequences, 0).getMethod(methodName);
        }
        //TODO handle if next method in more than one sequence, if both the same should be okay, otherwise error
        //(or decide something else to do)
        throw new IllegalStateException(String.format("found more than one sequence with next method %s", methodName));
    }

    private VerificationSequences addResult(final VerificationResult result) {
        final Collection<VerificationSequence> updatedSequences = sequences.stream()
                .map(sequence -> sequence.addResultIfHasNextMethod(result))
                .collect(Collectors.toList());
        return new VerificationSequences(updatedSequences);
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

}
