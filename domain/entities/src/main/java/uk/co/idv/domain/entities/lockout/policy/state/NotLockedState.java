package uk.co.idv.domain.entities.lockout.policy.state;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.UUID;

@RequiredArgsConstructor
public class NotLockedState implements LockoutState {

    private final String message;
    private final VerificationAttempts attempts;

    public NotLockedState(final VerificationAttempts attempts) {
        this("not locked", attempts);
    }

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
        return false;
    }

    @Override
    public VerificationAttempts getAttempts() {
        return attempts;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
