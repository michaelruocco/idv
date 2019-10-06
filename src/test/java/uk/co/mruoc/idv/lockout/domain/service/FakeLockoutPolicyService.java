package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

public class FakeLockoutPolicyService implements LockoutPolicyService {

    private CalculateLockoutStateRequest lastResetRequest;
    private CalculateLockoutStateRequest lastCalculateRequest;
    private VerificationAttempts resetAttemptsToReturn;
    private LockoutState stateToReturn;

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return false;
    }

    @Override
    public LockoutState calculateState(final CalculateLockoutStateRequest request) {
        lastCalculateRequest = request;
        return stateToReturn;
    }

    @Override
    public VerificationAttempts resetAttempts(final CalculateLockoutStateRequest request) {
        this.lastResetRequest = request;
        return resetAttemptsToReturn;
    }

    public CalculateLockoutStateRequest getLastResetRequest() {
        return lastResetRequest;
    }

    public CalculateLockoutStateRequest getLastCalculateRequest() {
        return lastCalculateRequest;
    }

    public void setResetAttemptsToReturn(final VerificationAttempts attempts) {
        resetAttemptsToReturn = attempts;
    }

    public void setStateToReturn(final LockoutState state) {
        this.stateToReturn = state;
    }
}
