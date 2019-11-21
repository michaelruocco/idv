package uk.co.idv.domain.entities.lockout.policy.hard;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

import java.util.UUID;

@RequiredArgsConstructor
public class HardLockoutState implements LockoutState {

    private final VerificationAttempts attempts;
    private final int maxNumberOfAttempts;

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
        return getNumberOfAttemptsRemaining() <= 0;
    }

    @Override
    public VerificationAttempts getAttempts() {
        return attempts;
    }

    @Override
    public String getMessage() {
        if (isLocked()) {
            return String.format("maximum number of attempts [%d] reached", maxNumberOfAttempts);
        }
        return String.format("%d attempts remaining", getNumberOfAttemptsRemaining());
    }

    public int getNumberOfAttemptsRemaining() {
        return maxNumberOfAttempts - attempts.size();
    }

    public int getMaxNumberOfAttempts() {
        return maxNumberOfAttempts;
    }

}
