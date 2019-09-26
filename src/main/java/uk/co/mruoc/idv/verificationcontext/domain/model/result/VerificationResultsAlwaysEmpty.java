package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

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

    @Override
    public boolean containsSuccessful(final String methodName) {
        return false;
    }

    @Override
    public Collection<String> getMethodNames() {
        return Collections.emptySet();
    }

    public int size() {
        return 0;
    }

    public VerificationResults add(final VerificationResult result) {
        return this;
    }

}
