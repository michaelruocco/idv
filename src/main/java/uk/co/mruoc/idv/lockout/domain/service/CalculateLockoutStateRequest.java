package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

@Builder
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

    @Override
    public CalculateLockoutStateRequest withAttempts(final VerificationAttempts updatedAttempts) {
        return CalculateLockoutStateRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .timestamp(timestamp)
                .alias(alias)
                .idvIdValue(idvIdValue)
                .attempts(updatedAttempts)
                .build();
    }

    public VerificationAttempts getAttempts() {
        return attempts;
    }

}
