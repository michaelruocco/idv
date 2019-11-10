package uk.co.idv.repository.mongo.lockout.policy.maxattempts;

import lombok.RequiredArgsConstructor;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocument;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentKeyConverter;
import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.MaxAttemptsAliasLevelLockoutPolicyParameters;

import java.util.UUID;

@RequiredArgsConstructor
public class MaxAttemptsAliasLevelLockoutPolicyDocumentConverter implements LockoutPolicyDocumentConverter {

    @Override
    public boolean supportsType(final String type) {
        return MaxAttemptsAliasLevelLockoutPolicyParameters.TYPE.equals(type);
    }

    @Override
    public DefaultLockoutPolicyParameters toParameters(final LockoutPolicyDocument policyDocument) {
        final String key = policyDocument.getKey();
        final MaxAttemptsLockoutPolicyDocument document = (MaxAttemptsLockoutPolicyDocument) policyDocument;
        return MaxAttemptsAliasLevelLockoutPolicyParameters.builder()
                .channelId(LockoutPolicyDocumentKeyConverter.extractChannelId(key))
                .activityName(LockoutPolicyDocumentKeyConverter.extractActivityName(key))
                .aliasType(LockoutPolicyDocumentKeyConverter.extractAliasType(key))
                .id(UUID.fromString(document.getId()))
                .recordAttemptStrategyType(document.getRecordAttemptStrategyType())
                .maxNumberOfAttempts(document.getMaxNumberOfAttempts())
                .build();
    }

    @Override
    public LockoutPolicyDocument toDocument(final LockoutPolicyParameters parameters) {
        final MaxAttemptsAliasLevelLockoutPolicyParameters maxAttemptsParameters = (MaxAttemptsAliasLevelLockoutPolicyParameters) parameters;
        final MaxAttemptsLockoutPolicyDocument document = new MaxAttemptsLockoutPolicyDocument();
        document.setKey(LockoutPolicyDocumentKeyConverter.toKey(maxAttemptsParameters));
        document.setId(maxAttemptsParameters.getId().toString());
        document.setLockoutType(maxAttemptsParameters.getLockoutType());
        document.setRecordAttemptStrategyType(maxAttemptsParameters.getRecordAttemptStrategyType());
        document.setMaxNumberOfAttempts(maxAttemptsParameters.getMaxNumberOfAttempts());
        document.setAliasLevel(maxAttemptsParameters.isAliasLevel());
        return document;
    }

}
