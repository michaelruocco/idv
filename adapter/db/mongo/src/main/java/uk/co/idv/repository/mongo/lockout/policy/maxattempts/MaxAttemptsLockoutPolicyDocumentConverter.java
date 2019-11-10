package uk.co.idv.repository.mongo.lockout.policy.maxattempts;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.state.MaxAttemptsLockoutStateCalculator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentKeyConverter;

import java.util.UUID;

@Builder
public class MaxAttemptsLockoutPolicyDocumentConverter implements LockoutPolicyDocumentConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;
    private final LockoutPolicyDocumentKeyConverter keyConverter;

    @Override
    public boolean supportsType(final String type) {
        return MaxAttemptsLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final LockoutPolicyDocument policyDocument) {
        final String key = policyDocument.getKey();
        final MaxAttemptsLockoutPolicyDocument document = (MaxAttemptsLockoutPolicyDocument) policyDocument;
        return DefaultLockoutPolicy.builder()
                .level(keyConverter.toLevel(key))
                .recordAttemptStrategy(toRecordAttemptStrategy(document))
                .stateCalculator(toStateCalculator(document))
                .id(UUID.fromString(document.getId()))
                .build();
    }

    private static LockoutStateCalculator toStateCalculator(final MaxAttemptsLockoutPolicyDocument document) {
        return new MaxAttemptsLockoutStateCalculator(document.getMaxNumberOfAttempts());
    }

    private RecordAttemptStrategy toRecordAttemptStrategy(final LockoutPolicyDocument document) {
        return recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType());
    }

    @Override
    public LockoutPolicyDocument toDocument(final LockoutPolicy policy) {
        final MaxAttemptsLockoutPolicyDocument document = new MaxAttemptsLockoutPolicyDocument();
        document.setKey(keyConverter.toKey(policy.getLockoutLevel()));
        document.setId(policy.getId().toString());
        document.setLockoutType(policy.getLockoutType());
        document.setRecordAttemptStrategyType(policy.getRecordAttemptStrategyType());
        final MaxAttemptsLockoutStateCalculator stateCalculator = (MaxAttemptsLockoutStateCalculator) policy.getStateCalculator();
        document.setMaxNumberOfAttempts(stateCalculator.getMaxAttempts());
        return document;
    }

}
