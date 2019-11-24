package uk.co.idv.repository.mongo.lockout.policy;


import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.repository.mongo.lockout.policy.hard.HardLockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.soft.RecurringSoftLockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.soft.SoftLockIntervalDocumentMother;
import uk.co.idv.repository.mongo.lockout.policy.soft.SoftLockoutPolicyDocument;

import java.util.Collections;

public class LockoutPolicyDocumentMother {

    public static LockoutPolicyDocument fake() {
        final LockoutPolicyDocument document = new LockoutPolicyDocument();
        populateCommonFields(document);
        document.setLockoutType("fake-lockout-type");
        return document;
    }

    public static HardLockoutPolicyDocument hardLock() {
        final HardLockoutPolicyDocument document = new HardLockoutPolicyDocument();
        populateCommonFields(document);
        document.setLockoutType(HardLockoutStateCalculator.TYPE);
        document.setMaxNumberOfAttempts(3);
        return document;
    }

    public static LockoutPolicyDocument nonLocking() {
        final LockoutPolicyDocument document = new LockoutPolicyDocument();
        populateCommonFields(document);
        document.setLockoutType(NonLockingLockoutStateCalculator.TYPE);
        return document;
    }

    public static SoftLockoutPolicyDocument softLock() {
        final SoftLockoutPolicyDocument document = new SoftLockoutPolicyDocument();
        populateCommonFields(document);
        document.setLockoutType(SoftLockoutStateCalculator.TYPE);
        document.setIntervals(SoftLockIntervalDocumentMother.collection(1));
        return document;
    }

    public static RecurringSoftLockoutPolicyDocument recurringSoftLock() {
        final RecurringSoftLockoutPolicyDocument document = new RecurringSoftLockoutPolicyDocument();
        populateCommonFields(document);
        document.setLockoutType(RecurringSoftLockoutStateCalculator.TYPE);
        document.setInterval(SoftLockIntervalDocumentMother.oneAttempt());
        return document;
    }

    private static void populateCommonFields(LockoutPolicyDocument document) {
        document.setId("0856d684-5038-409e-94e5-f415561c7226");
        document.setRecordAttemptStrategyType("fake-record-attempt-type");
        document.setChannelId("fake-channel");
        document.setActivityNames(Collections.singleton("fake-activity"));
        document.setAliasTypes(Collections.singleton("fake-alias"));
    }

}
