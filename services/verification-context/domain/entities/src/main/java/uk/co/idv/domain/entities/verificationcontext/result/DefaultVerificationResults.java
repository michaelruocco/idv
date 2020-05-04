package uk.co.idv.domain.entities.verificationcontext.result;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class DefaultVerificationResults implements VerificationResults {

    private final Collection<VerificationResult> results;

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
    public void add(final VerificationResult result) {
        results.add(result);
    }

    @Override
    public int size() {
        return results.size();
    }

    @Override
    public Stream<VerificationResult> toStream() {
        return results.stream();
    }

    @Override
    public Collection<VerificationResult> toCollection() {
        return Collections.unmodifiableCollection(results);
    }

}
