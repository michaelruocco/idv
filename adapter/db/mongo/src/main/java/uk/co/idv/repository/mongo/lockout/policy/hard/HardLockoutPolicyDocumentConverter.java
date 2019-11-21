package uk.co.idv.repository.mongo.lockout.policy.hard;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentKeyConverter;

import java.util.UUID;

@Builder
public class HardLockoutPolicyDocumentConverter implements LockoutPolicyDocumentConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;
    private final LockoutPolicyDocumentKeyConverter keyConverter;

    @Override
    public boolean supportsType(final String type) {
        return HardLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final LockoutPolicyDocument policyDocument) {
        final String key = policyDocument.getKey();
        final HardLockoutPolicyDocument document = (HardLockoutPolicyDocument) policyDocument;
        return new HardLockoutPolicy(
                UUID.fromString(document.getId()),
                keyConverter.toLevel(key),
                toRecordAttemptStrategy(document),
                document.getMaxNumberOfAttempts()
        );
    }

    private RecordAttemptStrategy toRecordAttemptStrategy(final LockoutPolicyDocument document) {
        return recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType());
    }

    @Override
    public LockoutPolicyDocument toDocument(final LockoutPolicy policy) {
        final HardLockoutPolicyDocument document = new HardLockoutPolicyDocument();
        document.setKey(keyConverter.toKey(policy.getLockoutLevel()));
        document.setId(policy.getId().toString());
        document.setLockoutType(policy.getLockoutType());
        document.setRecordAttemptStrategyType(policy.getRecordAttemptStrategyType());
        final HardLockoutStateCalculator stateCalculator = (HardLockoutStateCalculator) policy.getStateCalculator();
        document.setMaxNumberOfAttempts(stateCalculator.getMaxNumberOfAttempts());
        return document;
    }

}
