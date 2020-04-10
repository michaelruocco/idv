package uk.co.idv.domain.entities.lockout.policy.soft;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Builder
public class SoftLockoutState implements LockoutState {

    private final VerificationAttempts attempts;
    private final Duration duration;
    private final Instant lockedUntil;

    @Override
    public UUID getId() {
        return attempts.getId();
    }

    @Override
    public UUID getIdvId() {
        return attempts.getIdvId();
    }

    @Override
    public boolean isLocked() {
        return true;
    }

    @Override
    public VerificationAttempts getAttempts() {
        return attempts;
    }

    @Override
    public String getMessage() {
        return String.format("locked until %s", lockedUntil);
    }

    public Duration getDuration() {
        return duration;
    }

    public Instant getLockedUntil() {
        return lockedUntil;
    }

}
