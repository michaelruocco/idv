package uk.co.mruoc.idv.mongo.lockout.dao.policy;


import java.util.Collections;

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
        document.setLookups(Collections.singleton(buildLookup()));
    }

    private static LockoutPolicyLookupDocument buildLookup() {
        final LockoutPolicyLookupDocument document = new LockoutPolicyLookupDocument();
        document.setChannelId("fake-channel");
        document.setActivityName("fake-activity");
        document.setAliasType("fake-alias-type");
        return document;
    }

}
