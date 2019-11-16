package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

@Builder
@Getter
public class DefaultLockoutLevel implements LockoutLevel {

    public static final String TYPE = "channel-and-activity";

    private final String channelId;
    private final String activityName;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public boolean appliesTo(final LockoutRequest request) {
        return channelId.equals(request.getChannelId()) &&
                activityName.equals(request.getActivityName());
    }

    @Override
    public boolean isAliasLevel() {
        return false;
    }

}
