package uk.co.idv.domain.entities.lockout.policy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.util.Collection;
import java.util.Collections;

@Builder
@Getter
@AllArgsConstructor
@ToString
public class DefaultLockoutLevel implements LockoutLevel {

    private final String channelId;

    @Builder.Default
    private final Collection<String> activityNames = Collections.singleton(LockoutLevel.ALL);

    @Builder.Default
    private final Collection<String> aliasTypes = Collections.singleton(LockoutLevel.ALL);

    @Override
    public boolean appliesTo(final LockoutRequest request) {
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
