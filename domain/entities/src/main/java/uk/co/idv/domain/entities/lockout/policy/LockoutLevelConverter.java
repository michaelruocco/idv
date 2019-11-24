package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.DefaultLockoutPolicyRequest;
import uk.co.idv.domain.entities.lockout.DefaultLockoutPolicyRequest.DefaultLockoutPolicyRequestBuilder;
import uk.co.idv.domain.entities.lockout.LockoutPolicyRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class LockoutLevelConverter {

    public Collection<LockoutPolicyRequest> toPolicyRequests(final LockoutLevel level) {
        final Collection<LockoutPolicyRequest> requests = new ArrayList<>();
        final DefaultLockoutPolicyRequestBuilder builder = DefaultLockoutPolicyRequest.builder()
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
