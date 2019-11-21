package uk.co.idv.repository.mongo.lockout.policy.hard;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;

import java.util.UUID;

@RequiredArgsConstructor
public class HardLockoutPolicyDocumentConverter implements LockoutPolicyDocumentConverter {

    private final RecordAttemptStrategyFactory recordAttemptStrategyFactory;

    @Override
    public boolean supportsType(final String type) {
        return HardLockoutStateCalculator.TYPE.equals(type);
    }

    @Override
    public LockoutPolicy toPolicy(final LockoutPolicyDocument policyDocument) {
        final HardLockoutPolicyDocument document = (HardLockoutPolicyDocument) policyDocument;
        return new HardLockoutPolicy(
                UUID.fromString(document.getId()),
                toLevel(document),
                toRecordAttemptStrategy(document),
                document.getMaxNumberOfAttempts()
        );
    }

    private static LockoutLevel toLevel(final LockoutPolicyDocument document) {
        return DefaultLockoutLevel.builder()
                .activityNames(document.getActivityNames())
                .channelId(document.getChannelId())
                .aliasTypes(document.getAliasTypes())
                .build();
    }

    private RecordAttemptStrategy toRecordAttemptStrategy(final LockoutPolicyDocument document) {
        return recordAttemptStrategyFactory.build(document.getRecordAttemptStrategyType());
    }

    @Override
    public LockoutPolicyDocument toDocument(final LockoutPolicy policy) {
        final LockoutLevel level = policy.getLockoutLevel();
        final HardLockoutPolicyDocument document = new HardLockoutPolicyDocument();
        document.setChannelId(level.getChannelId());
        document.setActivityNames(level.getActivityNames());
        document.setAliasTypes(level.getAliasTypes());
        document.setId(policy.getId().toString());
        document.setLockoutType(policy.getLockoutType());
        document.setRecordAttemptStrategyType(policy.getRecordAttemptStrategyType());
        final HardLockoutStateCalculator stateCalculator = (HardLockoutStateCalculator) policy.getStateCalculator();
        document.setMaxNumberOfAttempts(stateCalculator.getMaxNumberOfAttempts());
        return document;
    }

}
