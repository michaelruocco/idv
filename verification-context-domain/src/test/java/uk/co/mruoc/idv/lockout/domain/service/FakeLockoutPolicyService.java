package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

public class FakeLockoutPolicyService implements LockoutPolicyService {

    private CalculateLockoutStateRequest lastResetRequest;
    private CalculateLockoutStateRequest lastCalculateRequest;
    private VerificationAttempts resetAttemptsToReturn;
    private LockoutState stateToReturn;
    private LockoutPolicyParameters lastAddedParameters;
    private LockoutPolicy lastAddedPolicy;

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

    @Override
    public void addPolicy(final LockoutPolicyParameters parameters) {
        this.lastAddedParameters = parameters;
    }

    @Override
    public void addPolicy(final LockoutPolicy policy) {
        this.lastAddedPolicy = policy;
    }

    public CalculateLockoutStateRequest getLastResetRequest() {
        return lastResetRequest;
    }

    public CalculateLockoutStateRequest getLastCalculateRequest() {
        return lastCalculateRequest;
    }

    public LockoutPolicyParameters getLastAddedParameters() {
        return lastAddedParameters;
    }

    public LockoutPolicy getLastAddedPolicy() {
        return lastAddedPolicy;
    }

    public void setResetAttemptsToReturn(final VerificationAttempts attempts) {
        resetAttemptsToReturn = attempts;
    }

    public void setStateToReturn(final LockoutState state) {
        this.stateToReturn = state;
    }
}
