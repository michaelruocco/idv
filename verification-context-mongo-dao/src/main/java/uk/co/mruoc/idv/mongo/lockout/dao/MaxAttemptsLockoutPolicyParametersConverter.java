package uk.co.mruoc.idv.mongo.lockout.dao;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.model.AbstractLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.MaxAttemptsLockoutPolicyParameters;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MaxAttemptsLockoutPolicyParametersConverter implements LockoutPolicyParametersConverter {

    @Override
    public boolean supportsType(final String type) {
        return MaxAttemptsLockoutPolicyParameters.TYPE.equals(type);
    }

    @Override
    public AbstractLockoutPolicyParameters toParameters(final LockoutPolicyDocument policyDocument) {
        final MaxAttemptsLockoutPolicyDocument document = (MaxAttemptsLockoutPolicyDocument) policyDocument;
        final Collection<LockoutLookupDocument> lookupDocuments = document.getLookups();
        return MaxAttemptsLockoutPolicyParameters.builder()
                .id(UUID.fromString(document.getId()))
                .recordAttemptStrategyType(document.getRecordAttemptStrategyType())
                .channelIds(toChannelIds(lookupDocuments))
                .activityNames(toActivityNames(lookupDocuments))
                .aliasTypes(toAliasTypes(lookupDocuments))
                .maxNumberOfAttempts(document.getMaxNumberOfAttempts())
                .build();
    }

    @Override
    public LockoutPolicyDocument toDocument(final AbstractLockoutPolicyParameters parameters) {
        final MaxAttemptsLockoutPolicyParameters maxAttemptsParameters = (MaxAttemptsLockoutPolicyParameters) parameters;
        final MaxAttemptsLockoutPolicyDocument document = new MaxAttemptsLockoutPolicyDocument();
        document.setId(maxAttemptsParameters.getId().toString());
        document.setLockoutType(maxAttemptsParameters.getLockoutType());
        document.setRecordAttemptStrategyType(maxAttemptsParameters.getRecordAttemptStrategyType());
        document.setLookups(toLookupDocuments(parameters));
        document.setMaxNumberOfAttempts(maxAttemptsParameters.getMaxNumberOfAttempts());
        return document;
    }

    private Collection<LockoutLookupDocument> toLookupDocuments(final AbstractLockoutPolicyParameters parameters) {
        final Collection<LockoutLookupDocument> documents = new HashSet<>();
        for (final String channelId : parameters.getChannelIds()) {
            for (final String activityName : parameters.getActivityNames()) {
                for (final String aliasType : parameters.getAliasTypes()) {
                    documents.add(new LockoutLookupDocument(channelId, activityName, aliasType));
                }
            }
        }
        return Collections.unmodifiableCollection(documents);
    }

    private Collection<String> toChannelIds(final Collection<LockoutLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutLookupDocument::getChannelId);
    }

    private Collection<String> toActivityNames(final Collection<LockoutLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutLookupDocument::getActivityName);
    }

    private Collection<String> toAliasTypes(final Collection<LockoutLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutLookupDocument::getAliasType);
    }

    private Collection<String> toUniqueStrings(final Collection<LockoutLookupDocument> documents,
                                               final Function<LockoutLookupDocument, String> stringExtractor) {
        return documents.stream()
                .map(stringExtractor)
                .collect(Collectors.toSet());
    }

}
