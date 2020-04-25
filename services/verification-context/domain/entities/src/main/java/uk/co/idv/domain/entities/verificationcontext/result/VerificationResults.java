package uk.co.idv.domain.entities.verificationcontext.result;

import java.util.Collection;
import java.util.stream.Stream;

public interface VerificationResults extends Iterable<VerificationResult> {

    boolean isEmpty();

    boolean containsSuccessful();

    boolean containsSuccessful(final String methodName);

    Collection<String> getMethodNames();

    VerificationResults add(final VerificationResult result);

    int size();

    Stream<VerificationResult> toStream();

    Collection<VerificationResult> toCollection();

}
