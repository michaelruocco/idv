package uk.co.idv.domain.entities.verificationcontext.result;

public class VerificationResultsMother {

    public static VerificationResults empty() {
        return new DefaultVerificationResults();
    }

    public static VerificationResults oneSuccessful(final String methodName) {
        return new DefaultVerificationResults(successful(methodName));
    }

    public static VerificationResult successful(final String methodName) {
        return new FakeVerificationResultSuccessful(methodName);
    }

    public static VerificationResults oneFailed(final String methodName) {
        return new DefaultVerificationResults(new FakeVerificationResultFailed(methodName));
    }

}
