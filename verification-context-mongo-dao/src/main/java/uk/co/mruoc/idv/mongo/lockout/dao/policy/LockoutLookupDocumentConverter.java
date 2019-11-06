package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LockoutLookupDocumentConverter {

    public Collection<LockoutLookupDocument> toLookupDocuments(final LockoutPolicyParameters parameters) {
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

    public Collection<String> toChannelIds(final Collection<LockoutLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutLookupDocument::getChannelId);
    }

    public Collection<String> toActivityNames(final Collection<LockoutLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutLookupDocument::getActivityName);
    }

    public Collection<String> toAliasTypes(final Collection<LockoutLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutLookupDocument::getAliasType);
    }

    private Collection<String> toUniqueStrings(final Collection<LockoutLookupDocument> documents,
                                               final Function<LockoutLookupDocument, String> stringExtractor) {
        return documents.stream()
                .map(stringExtractor)
                .collect(Collectors.toSet());
    }

}
