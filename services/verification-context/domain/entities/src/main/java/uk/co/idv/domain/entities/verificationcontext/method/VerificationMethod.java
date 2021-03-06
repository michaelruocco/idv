package uk.co.idv.domain.entities.verificationcontext.method;

import lombok.Getter;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.Eligibility;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;

import java.time.Duration;
import java.util.Optional;

public interface VerificationMethod {

    String getName();

    VerificationMethodParams getParams();

    int getMaxAttempts();

    Duration getDuration();

    boolean isEligible();

    Optional<String> getEligibilityReason();

    Eligibility getEligibility();

    boolean hasResults();

    boolean isComplete();

    boolean isSuccessful();

    VerificationResults getResults();

    void addResult(final VerificationResult result);

    default boolean hasName(final String otherName) {
        return getName().equals(otherName);
    }

    @Getter
    class CannotAddResultToMethodException extends RuntimeException {

        private final String resultMethodName;
        private final String methodName;

        public CannotAddResultToMethodException(final String resultMethodName, final String methodName) {
            super(String.format("cannot add result for method %s to method %s", resultMethodName, methodName));
            this.resultMethodName = resultMethodName;
            this.methodName = methodName;
        }

    }

    class CannotAddResultToIneligibleMethodException extends RuntimeException {

        public CannotAddResultToIneligibleMethodException(final String methodName) {
            super(methodName);
        }

    }

    class MethodAlreadyCompleteException extends RuntimeException {

        public MethodAlreadyCompleteException(final String methodName) {
            super(methodName);
        }

    }

}
