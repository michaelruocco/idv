package uk.co.mruoc.idv.verificationcontext.domain.model.result;

import java.util.Collection;

public interface VerificationResults extends Iterable<VerificationResult> {

    boolean isEmpty();

    boolean containsSuccessful();

    boolean containsSuccessful(final String methodName);

    Collection<String> getMethodNames();

    VerificationResults add(final VerificationResult result);

    int size();

}
