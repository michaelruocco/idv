package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

@Builder
@Getter
public class AliasLockoutLevel implements LockoutLevel {

    private final String channelId;
    private final String activityName;
    private final String aliasType;

    @Override
    public String getType() {
        return "channel-activity-and-alias";
    }

    @Override
    public boolean appliesTo(LockoutRequest request) {
        return channelId.equals(request.getChannelId()) &&
                activityName.equals(request.getActivityName()) &&
                aliasType.equals(request.getAliasType());
    }

    @Override
    public boolean includesAlias() {
        return true;
    }

}
