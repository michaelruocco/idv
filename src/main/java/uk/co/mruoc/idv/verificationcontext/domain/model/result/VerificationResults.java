package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class VerificationResults implements Iterable<VerificationResult> {

    private final List<VerificationResult> results;

    public VerificationResults(final VerificationResult... results) {
        this(Arrays.asList(results));
    }

    public VerificationResults(final VerificationResults results) {
        this(results.results);
    }

    public VerificationResults(final Collection<VerificationResult> results) {
        this.results = new ArrayList<>(results);
    }

    @Override
    public Iterator<VerificationResult> iterator() {
        return results.iterator();
    }

    public boolean isEmpty() {
        return results.isEmpty();
    }

    public boolean containsSuccessful() {
        return results.stream().anyMatch(VerificationResult::isSuccessful);
    }

    public Collection<String> getMethodNames() {
        return results.stream()
                .map(VerificationResult::getMethodName)
                .collect(Collectors.toSet());
    }

    public boolean hasSuccessfulResult(final String methodName) {
        return results.stream()
                .filter(result -> result.hasMethodName(methodName))
                .anyMatch(VerificationResult::isSuccessful);
    }

    public VerificationResults add(final VerificationResult result) {
        final Collection<VerificationResult> updatedResults = new ArrayList<>(results);
        updatedResults.add(result);
        return new VerificationResults(updatedResults);
    }

}
