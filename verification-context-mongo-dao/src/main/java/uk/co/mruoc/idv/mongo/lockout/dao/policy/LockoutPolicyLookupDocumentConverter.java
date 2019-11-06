package uk.co.mruoc.idv.mongo.lockout.dao.policy;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LockoutPolicyLookupDocumentConverter {

    public Collection<LockoutPolicyLookupDocument> toLookupDocuments(final LockoutPolicyParameters parameters) {
        final Collection<LockoutPolicyLookupDocument> documents = new HashSet<>();
        for (final String channelId : parameters.getChannelIds()) {
            for (final String activityName : parameters.getActivityNames()) {
                for (final String aliasType : parameters.getAliasTypes()) {
                    documents.add(new LockoutPolicyLookupDocument(channelId, activityName, aliasType));
                }
            }
        }
        return Collections.unmodifiableCollection(documents);
    }

    public Collection<String> toChannelIds(final Collection<LockoutPolicyLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutPolicyLookupDocument::getChannelId);
    }

    public Collection<String> toActivityNames(final Collection<LockoutPolicyLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutPolicyLookupDocument::getActivityName);
    }

    public Collection<String> toAliasTypes(final Collection<LockoutPolicyLookupDocument> documents) {
        return toUniqueStrings(documents, LockoutPolicyLookupDocument::getAliasType);
    }

    private Collection<String> toUniqueStrings(final Collection<LockoutPolicyLookupDocument> documents,
                                               final Function<LockoutPolicyLookupDocument, String> stringExtractor) {
        return documents.stream()
                .map(stringExtractor)
                .collect(Collectors.toSet());
    }

}
