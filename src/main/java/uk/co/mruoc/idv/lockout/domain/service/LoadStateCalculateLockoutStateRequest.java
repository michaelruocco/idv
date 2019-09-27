package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

@Builder
public class LoadStateCalculateLockoutStateRequest implements CalculateLockoutStateRequest {

    private final LoadLockoutStateRequest loadStateRequest;
    private final VerificationAttempts attempts;

    @Override
    public String getChannelId() {
        return loadStateRequest.getChannelId();
    }

    @Override
    public String getActivityName() {
        return loadStateRequest.getActivityName();
    }

    @Override
    public Instant getTimestamp() {
        return loadStateRequest.getTimestamp();
    }

    @Override
    public Alias getAlias() {
        return loadStateRequest.getAlias();
    }

    @Override
    public UUID getIdvIdValue() {
        return loadStateRequest.getIdvIdValue();
    }

    @Override
    public VerificationAttempts getAttempts() {
        return attempts;
    }

}
