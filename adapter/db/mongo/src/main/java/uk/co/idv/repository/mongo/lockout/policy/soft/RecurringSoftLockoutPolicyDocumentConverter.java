package uk.co.idv.repository.mongo.lockout.policy.soft;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.soft.RecurringSoftLockoutStateCalculator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;

import java.util.UUID;

@RequiredArgsConstructor
public class RecurringSoftLockoutPolicyDocumentConverter implements LockoutPolicyDocumentConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;
    private final SoftLockIntervalDocumentsConverter intervalDocumentsConverter;

    @Override
    public boolean supportsType(final String type) {
        return RecurringSoftLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final LockoutPolicyDocument policyDocument) {
        final RecurringSoftLockoutPolicyDocument document = (RecurringSoftLockoutPolicyDocument) policyDocument;
        return new RecurringSoftLockoutPolicy(
                UUID.fromString(document.getId()),
                toLevel(document),
                toRecordAttemptStrategy(document),
                intervalDocumentsConverter.toInterval(document.getInterval())
        );
    }

    @Override
    public LockoutPolicyDocument toDocument(final LockoutPolicy policy) {
        final LockoutLevel level = policy.getLockoutLevel();
        final RecurringSoftLockoutPolicyDocument document = new RecurringSoftLockoutPolicyDocument();
        document.setChannelId(level.getChannelId());
        document.setActivityNames(level.getActivityNames());
        document.setAliasTypes(level.getAliasTypes());
        document.setId(policy.getId().toString());
        document.setLockoutType(policy.getLockoutType());
        document.setRecordAttemptStrategyType(policy.getRecordAttemptStrategyType());
        final RecurringSoftLockoutStateCalculator stateCalculator = (RecurringSoftLockoutStateCalculator) policy.getStateCalculator();
        document.setInterval(intervalDocumentsConverter.toDocument(stateCalculator.getInterval()));
        return document;
    }

    private RecordAttemptStrategy toRecordAttemptStrategy(final LockoutPolicyDocument document) {
        return recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType());
    }

}
