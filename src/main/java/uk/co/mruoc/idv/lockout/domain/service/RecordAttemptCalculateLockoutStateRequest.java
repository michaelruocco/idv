package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempt;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

@Builder
public class RecordAttemptCalculateLockoutStateRequest implements CalculateLockoutStateRequest {

    private final VerificationAttempt attempt;
    private final VerificationAttempts attempts;

    @Override
    public String getChannelId() {
        return attempt.getChannelId();
    }

    @Override
    public String getActivityName() {
        return attempt.getActivityName();
    }

    @Override
    public Instant getTimestamp() {
        return attempt.getTimestamp();
    }

    @Override
    public Alias getAlias() {
        return attempt.getProvidedAlias();
    }

    @Override
    public UUID getIdvIdValue() {
        return attempt.getIdvIdValue();
    }

    @Override
    public VerificationAttempts getAttempts() {
        return attempts;
    }

}
