package uk.co.idv.repository.mongo.lockout.policy;


import uk.co.idv.repository.mongo.lockout.policy.maxattempts.MaxAttemptsLockoutPolicyDocument;

public class LockoutPolicyDocumentMother {

    public static LockoutPolicyDocument fake() {
        final LockoutPolicyDocument document = new LockoutPolicyDocument();
        populateCommonFields(document);
        document.setLockoutType("fake-lockout-type");
        return document;
    }

    public static MaxAttemptsLockoutPolicyDocument maxAttempts() {
        final MaxAttemptsLockoutPolicyDocument document = new MaxAttemptsLockoutPolicyDocument();
        populateCommonFields(document);
        document.setLockoutType("max-attempts");
        document.setMaxNumberOfAttempts(3);
        return document;
    }

    private static void populateCommonFields(LockoutPolicyDocument document) {
        document.setId("0856d684-5038-409e-94e5-f415561c7226");
        document.setRecordAttemptStrategyType("fake-record-attempt-type");
        document.setKey("fake-channel*fake-activity*fake-alias");
    }

}
