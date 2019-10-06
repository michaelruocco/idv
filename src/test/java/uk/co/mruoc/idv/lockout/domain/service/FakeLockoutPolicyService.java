package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

public class FakeLockoutPolicyService implements LockoutPolicyService {

    private CalculateLockoutStateRequest lastResetRequest;
    private VerificationAttempts resetAttemptsToReturn;

    @Override
    public boolean shouldRecordAttempt(final RecordAttemptRequest request) {
        return false;
    }

    @Override
    public LockoutState calculateState(final CalculateLockoutStateRequest request) {
        return null;
    }

    @Override
    public VerificationAttempts resetAttempts(final CalculateLockoutStateRequest request) {
        this.lastResetRequest = request;
        return resetAttemptsToReturn;
    }

    public CalculateLockoutStateRequest getLastResetRequest() {
        return lastResetRequest;
    }

    public void setResetAttemptsToReturn(final VerificationAttempts attempts) {
        resetAttemptsToReturn = attempts;
    }

}
