package uk.co.idv.repository.mongo.verificationcontext.result;

public class VerificationResultDocumentMother {

    public static VerificationResultDocument successful() {
        final VerificationResultDocument document = buildDefault();
        document.setSuccessful(true);
        return document;
    }

    public static VerificationResultDocument failed() {
        final VerificationResultDocument document = buildDefault();
        document.setSuccessful(false);
        return document;
    }

    private static VerificationResultDocument buildDefault() {
        return new DefaultVerificationResultDocument();
    }

    private static class DefaultVerificationResultDocument extends VerificationResultDocument {

        private DefaultVerificationResultDocument() {
            this("fake-method");
        }

        private DefaultVerificationResultDocument(final String methodName) {
            setMethodName(methodName);
            setVerificationId("524d0e6d-cead-49f3-b0f5-04e29e789681");
            setTimestamp("2019-09-24T15:52:49.569287Z");
        }

    }

}
