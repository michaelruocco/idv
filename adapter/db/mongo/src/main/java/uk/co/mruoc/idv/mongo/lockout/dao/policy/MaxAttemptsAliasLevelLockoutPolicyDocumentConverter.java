package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.model.DefaultLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsAliasLevelLockoutPolicyParameters;

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
