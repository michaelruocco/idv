package uk.co.idv.domain.entities.lockout.policy.state;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import lombok.ToString;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

@Builder
@RequiredArgsConstructor
@ToString
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

    public Instant calculateLockedUntil(final Duration duration) {
        return attempts.getMostRecentTimestamp().plus(duration);
    }

    public boolean wasIssuedBefore(final Instant instant) {
        return this.timestamp.isBefore(instant);
    }

    public CalculateLockoutStateRequest updateAttempts(final VerificationAttempts attempts) {
        return CalculateLockoutStateRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .timestamp(timestamp)
                .alias(alias)
                .idvIdValue(idvIdValue)
                .attempts(attempts)
                .build();
    }

}
