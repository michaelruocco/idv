package uk.co.idv.domain.entities.lockout.policy;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

@Builder
@Getter
public class DefaultLockoutLevel implements LockoutLevel {

    private final String channelId;
    private final String activityName;

    @Override
    public String getType() {
        return "channel-and-activity";
    }

    @Override
    public boolean appliesTo(LockoutRequest request) {
        return channelId.equals(request.getChannelId()) &&
                activityName.equals(request.getActivityName());
    }

    @Override
    public boolean includesAlias() {
        return false;
    }

}
