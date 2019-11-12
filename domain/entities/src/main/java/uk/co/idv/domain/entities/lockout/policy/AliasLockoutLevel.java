package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

@Builder
@Getter
public class AliasLockoutLevel implements LockoutLevel {

    public static final String TYPE = "channel-activity-and-alias";

    private final String channelId;
    private final String activityName;
    private final String aliasType;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean appliesTo(final LockoutRequest request) {
        return channelId.equals(request.getChannelId()) &&
                activityName.equals(request.getActivityName()) &&
                aliasType.equals(request.getAliasType());
    }

    @Override
    public boolean includesAlias() {
        return true;
    }

}
