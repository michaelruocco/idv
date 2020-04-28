package uk.co.idv.domain.entities.policy;

import uk.co.idv.domain.entities.policy.DefaultPolicyRequest.DefaultPolicyRequestBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LockoutLevelConverter {

    public Collection<PolicyRequest> toPolicyRequests(final PolicyLevel level) {
        final Collection<PolicyRequest> requests = new ArrayList<>();
        final DefaultPolicyRequestBuilder builder = DefaultPolicyRequest.builder()
                .channelId(level.getChannelId());
        for (final String activityName : level.getActivityNames()) {
            builder.activityName(activityName);
            for (final String aliasType : level.getAliasTypes()) {
                builder.aliasType(aliasType);
                requests.add(builder.build());
            }
        }
        return Collections.unmodifiableCollection(requests);
    }

}
