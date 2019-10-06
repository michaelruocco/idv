package uk.co.mruoc.idv.lockout.domain.model;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class LockoutStateMaxAttempts implements LockoutState {

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
