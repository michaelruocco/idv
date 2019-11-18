package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;

import java.time.Instant;
import java.util.UUID;

@Builder
public class DefaultLoadLockoutStateRequest implements LockoutStateRequest {

    private final String channelId;
    private final String activityName;
    private final Alias alias;
    private final Instant timestamp;
    private final UUID idvIdValue;

    @Override
    public String getChannelId() {
        return channelId;
    }

    @Override
    public String getActivityName() {
        return activityName;
    }

    @Override
    public Alias getAlias() {
        return alias;
    }

    @Override
    public String getAliasType() {
        return alias.getType();
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public UUID getIdvIdValue() {
        return idvIdValue;
    }

}
