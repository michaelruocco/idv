package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

@Builder
public class CalculateLockoutStateRequest {

    private final String channelId;
    private final String activityName;
    private final Instant timestamp;
    private final Alias alias;
    private final UUID idvIdValue;
    private final VerificationAttempts attempts;

    public String getChannelId() {
        return channelId;
    }

    public String getActivityName() {
        return activityName;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Alias getAlias() {
        return alias;
    }

    public UUID getIdvIdValue() {
        return idvIdValue;
    }

    public VerificationAttempts getAttempts() {
        return attempts;
    }

}
