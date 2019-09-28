package uk.co.mruoc.idv.lockout.domain.service;

import lombok.RequiredArgsConstructor;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.LockoutType;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

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
    public LockoutType getType() {
        return LockoutType.MAX_ATTEMPTS;
    }

    @Override
    public boolean isLocked() {
        return getNumberOfAttemptsRemaining() <= 0;
    }

    @Override
    public VerificationAttempts getAttempts() {
        return attempts;
    }

    public int getNumberOfAttemptsRemaining() {
        return maxNumberOfAttempts - attempts.size();
    }

}
