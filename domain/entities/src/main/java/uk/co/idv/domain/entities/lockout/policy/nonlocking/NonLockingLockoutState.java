package uk.co.idv.domain.entities.lockout.policy.nonlocking;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

import java.util.UUID;

@RequiredArgsConstructor
public class NonLockingLockoutState implements LockoutState {

    private final VerificationAttempts attempts;

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
        return "non locking policy";
    }

}
