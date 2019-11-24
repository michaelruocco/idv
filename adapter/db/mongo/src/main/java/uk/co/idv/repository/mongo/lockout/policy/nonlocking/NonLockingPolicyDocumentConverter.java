package uk.co.idv.repository.mongo.lockout.policy.nonlocking;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.nonlocking.NonLockingLockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;

import java.util.UUID;

@RequiredArgsConstructor
public class NonLockingPolicyDocumentConverter implements LockoutPolicyDocumentConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    @Override
    public boolean supportsType(final String type) {
        return NonLockingLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final LockoutPolicyDocument document) {
        return new NonLockingLockoutPolicy(
                UUID.fromString(document.getId()),
                toLevel(document),
                recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType())
        );
    }

    @Override
    public LockoutPolicyDocument toDocument(final LockoutPolicy policy) {
        final LockoutLevel level = policy.getLockoutLevel();
        final LockoutPolicyDocument document = new LockoutPolicyDocument();
        document.setChannelId(level.getChannelId());
        document.setActivityNames(level.getActivityNames());
        document.setAliasTypes(level.getAliasTypes());
        document.setId(policy.getId().toString());
        document.setLockoutType(policy.getLockoutType());
        document.setRecordAttemptStrategyType(policy.getRecordAttemptStrategyType());
        return document;
    }

}
