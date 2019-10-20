package uk.co.mruoc.idv.mongo.verificationcontext.dao.result;

public class FakeVerificationResultSuccessfulDocument extends VerificationResultDocument {

    public FakeVerificationResultSuccessfulDocument() {
        this("fake-method");
    }

    public FakeVerificationResultSuccessfulDocument(final String methodName) {
        setMethodName(methodName);
        setVerificationId("524d0e6d-cead-49f3-b0f5-04e29e789681");
        setTimestamp("2019-09-24T15:52:49.569287Z");
        setSuccessful(true);
    }

}
