package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DefaultVerificationResults implements VerificationResults {

    private final List<VerificationResult> results;

    public DefaultVerificationResults(final VerificationResult... results) {
        this(Arrays.asList(results));
    }

    public DefaultVerificationResults(final Collection<VerificationResult> results) {
        this.results = new ArrayList<>(results);
    }

    @Override
    public Iterator<VerificationResult> iterator() {
        return results.iterator();
    }

    @Override
    public boolean isEmpty() {
        return results.isEmpty();
    }

    @Override
    public boolean containsSuccessful() {
        return results.stream().anyMatch(VerificationResult::isSuccessful);
    }

    @Override
    public boolean containsSuccessful(final String methodName) {
        return results.stream()
                .filter(result -> result.hasMethodName(methodName))
                .anyMatch(VerificationResult::isSuccessful);
    }

    @Override
    public Collection<String> getMethodNames() {
        return results.stream()
                .map(VerificationResult::getMethodName)
                .collect(Collectors.toSet());
    }

    @Override
    public VerificationResults add(final VerificationResult result) {
        final Collection<VerificationResult> updatedResults = new ArrayList<>(results);
        updatedResults.add(result);
        return new DefaultVerificationResults(updatedResults);
    }

    @Override
    public int size() {
        return results.size();
    }

    @Override
    public Stream<VerificationResult> stream() {
        return results.stream();
    }

}
