package uk.co.idv.repository.mongo.lockout.policy.soft;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.SoftLockoutStateCalculator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;

import java.util.UUID;

@RequiredArgsConstructor
public class SoftLockoutPolicyDocumentConverter implements LockoutPolicyDocumentConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;
    private final SoftLockIntervalDocumentsConverter intervalDocumentsConverter;

    @Override
    public boolean supportsType(final String type) {
        return SoftLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final LockoutPolicyDocument policyDocument) {
        final SoftLockoutPolicyDocument document = (SoftLockoutPolicyDocument) policyDocument;
        return new SoftLockoutPolicy(
                UUID.fromString(document.getId()),
                toLevel(document),
                toRecordAttemptStrategy(document),
                intervalDocumentsConverter.toIntervals(document.getIntervals())
        );
    }

    @Override
    public LockoutPolicyDocument toDocument(final LockoutPolicy policy) {
        final LockoutLevel level = policy.getLockoutLevel();
        final SoftLockoutPolicyDocument document = new SoftLockoutPolicyDocument();
        document.setChannelId(level.getChannelId());
        document.setActivityNames(level.getActivityNames());
        document.setAliasTypes(level.getAliasTypes());
        document.setId(policy.getId().toString());
        document.setLockoutType(policy.getLockoutType());
        document.setRecordAttemptStrategyType(policy.getRecordAttemptStrategyType());
        final SoftLockoutStateCalculator stateCalculator = (SoftLockoutStateCalculator) policy.getStateCalculator();
        document.setIntervals(intervalDocumentsConverter.toDocuments(stateCalculator.getIntervals()));
        return document;
    }

    private RecordAttemptStrategy toRecordAttemptStrategy(final LockoutPolicyDocument document) {
        return recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType());
    }

}
