package uk.co.mruoc.idv.verificationcontext.domain.model.method;

import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

public interface VerificationMethod {

    String getName();

    int getMaxAttempts();

    Duration getDuration();

    boolean isEligible();

    Optional<String> getEligibilityReason();

    Eligibility getEligibility();

    boolean hasName(final String otherName);

    boolean hasResults();

    boolean isComplete();

    boolean isSuccessful();

    VerificationResults getResults();

    VerificationMethod addResult(final VerificationResult result);

    class CannotAddResultToMethodException extends RuntimeException {

        public CannotAddResultToMethodException(final String resultMethodName, final String methodName) {
            super(String.format("cannot add result for method %s to method %s", resultMethodName, methodName));
        }

    }

    class CannotAddResultToIneligibleMethodException extends RuntimeException {

        public CannotAddResultToIneligibleMethodException(final String methodName) {
            super(String.format("cannot add result to ineligible method %s", methodName));
        }

    }

}
