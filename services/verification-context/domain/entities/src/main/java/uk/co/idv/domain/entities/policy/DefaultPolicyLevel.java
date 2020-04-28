package uk.co.idv.domain.entities.policy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;

@Builder
@Data
@AllArgsConstructor
public class DefaultPolicyLevel implements PolicyLevel {

    private final String channelId;

    @Builder.Default
    private final Collection<String> activityNames = Collections.singleton(PolicyLevel.ALL);

    @Builder.Default
    private final Collection<String> aliasTypes = Collections.singleton(PolicyLevel.ALL);

    @Override
    public boolean appliesTo(final PolicyRequest request) {
        return channelId.equals(request.getChannelId()) &&
                appliesToActivity(request.getActivityName()) &&
                appliesToAlias(request.getAliasType());
    }

    private boolean appliesToActivity(final String activityName) {
        return activityNames.contains(activityName) || activityNames.contains(ALL);
    }

    private boolean appliesToAlias(final String aliasType) {
        return aliasTypes.contains(aliasType) || aliasTypes.contains(ALL);
    }

    @Override
    public boolean isAliasLevel() {
        return !aliasTypes.contains(ALL);
    }

}
