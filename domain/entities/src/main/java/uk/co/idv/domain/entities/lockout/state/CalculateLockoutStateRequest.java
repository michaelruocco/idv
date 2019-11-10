package uk.co.idv.domain.entities.lockout.state;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

@Builder
@RequiredArgsConstructor
public class CalculateLockoutStateRequest implements LockoutStateRequest {

    private final String channelId;
    private final String activityName;
    private final Instant timestamp;
    private final Alias alias;
    private final UUID idvIdValue;
    private final VerificationAttempts attempts;

    @Override
    public String getChannelId() {
        return channelId;
    }

    @Override
    public String getActivityName() {
        return activityName;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
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
    public UUID getIdvIdValue() {
        return idvIdValue;
    }

    public VerificationAttempts getAttempts() {
        return attempts;
    }

}
