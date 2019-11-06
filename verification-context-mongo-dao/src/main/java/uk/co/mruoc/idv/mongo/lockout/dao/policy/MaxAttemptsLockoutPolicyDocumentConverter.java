package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
public class MaxAttemptsLockoutPolicyDocumentConverter implements LockoutPolicyDocumentConverter {

    private final LockoutPolicyLookupDocumentConverter lookupConverter;

    @Override
    public boolean supportsType(final String type) {
        return MaxAttemptsLockoutPolicyParameters.TYPE.equals(type);
    }

    @Override
    public LockoutPolicyParameters toParameters(final LockoutPolicyDocument policyDocument) {
        final MaxAttemptsLockoutPolicyDocument document = (MaxAttemptsLockoutPolicyDocument) policyDocument;
        final Collection<LockoutPolicyLookupDocument> lookupDocuments = document.getLookups();
        return MaxAttemptsLockoutPolicyParameters.builder()
                .id(UUID.fromString(document.getId()))
                .recordAttemptStrategyType(document.getRecordAttemptStrategyType())
                .channelIds(lookupConverter.toChannelIds(lookupDocuments))
                .activityNames(lookupConverter.toActivityNames(lookupDocuments))
                .aliasTypes(lookupConverter.toAliasTypes(lookupDocuments))
                .maxNumberOfAttempts(document.getMaxNumberOfAttempts())
                .build();
    }

    @Override
    public LockoutPolicyDocument toDocument(final LockoutPolicyParameters parameters) {
        final MaxAttemptsLockoutPolicyParameters maxAttemptsParameters = (MaxAttemptsLockoutPolicyParameters) parameters;
        final MaxAttemptsLockoutPolicyDocument document = new MaxAttemptsLockoutPolicyDocument();
        document.setId(maxAttemptsParameters.getId().toString());
        document.setLockoutType(maxAttemptsParameters.getLockoutType());
        document.setRecordAttemptStrategyType(maxAttemptsParameters.getRecordAttemptStrategyType());
        document.setLookups(lookupConverter.toLookupDocuments(parameters));
        document.setMaxNumberOfAttempts(maxAttemptsParameters.getMaxNumberOfAttempts());
        return document;
    }

}
