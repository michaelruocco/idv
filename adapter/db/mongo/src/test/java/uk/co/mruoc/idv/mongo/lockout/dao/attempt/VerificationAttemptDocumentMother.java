package uk.co.mruoc.idv.mongo.lockout.dao.attempt;

import uk.co.mruoc.idv.mongo.identity.dao.AliasDocumentMother;

import java.util.Arrays;
import java.util.UUID;

public class VerificationAttemptDocumentMother {

    public static VerificationAttemptsDocument attemptsDocument(final VerificationAttemptDocument... documents) {
        final VerificationAttemptsDocument document = new VerificationAttemptsDocument();
        document.setId(UUID.randomUUID().toString());
        document.setIdvId(UUID.randomUUID().toString());
        document.setAttempts(Arrays.asList(documents));
        return document;
    }

    public static VerificationAttemptDocument successful() {
        final VerificationAttemptDocument document = buildDefault();
        document.setSuccessful(true);
        return document;
    }

    public static VerificationAttemptDocument failed() {
        final VerificationAttemptDocument document = buildDefault();
        document.setSuccessful(false);
        return document;
    }

    private static VerificationAttemptDocument buildDefault() {
        return new DefaultVerificationAttemptDocument();
    }

    private static class DefaultVerificationAttemptDocument extends VerificationAttemptDocument {

        private DefaultVerificationAttemptDocument() {
            this("fake-method");
        }

        private DefaultVerificationAttemptDocument(final String methodName) {
            setContextId("b240b5fc-ce17-402e-95d2-b0948820f5e6");
            setChannelId("fake-channel");
            setActivityName("fake-activity");
            setAlias(AliasDocumentMother.creditCard());
            setIdvIdValue("f8a797c8-4e15-4460-8130-858ece628f8b");
            setMethodName(methodName);
            setVerificationId("524d0e6d-cead-49f3-b0f5-04e29e789681");
            setTimestamp("2019-09-24T15:52:49.569287Z");
        }

    }

}
