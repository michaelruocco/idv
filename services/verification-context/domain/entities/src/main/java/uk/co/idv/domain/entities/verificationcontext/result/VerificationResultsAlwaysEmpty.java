package uk.co.idv.domain.entities.verificationcontext.result;


import lombok.EqualsAndHashCode;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.stream.Stream;

@EqualsAndHashCode
public class VerificationResultsAlwaysEmpty implements VerificationResults {

    @Override
    public Iterator<VerificationResult> iterator() {
        return Collections.emptyIterator();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean containsSuccessful() {
        return false;
    }

    public int size() {
        return 0;
    }

    @Override
    public Stream<VerificationResult> toStream() {
        return Stream.empty();
    }

    @Override
    public Collection<VerificationResult> toCollection() {
        return Collections.emptyList();
    }

    public VerificationResults add(final VerificationResult result) {
        return this;
    }

}
