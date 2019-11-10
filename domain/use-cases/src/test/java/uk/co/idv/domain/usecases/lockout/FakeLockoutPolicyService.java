package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.Collection;

public class FakeLockoutPolicyService implements LockoutPolicyService {

    private CalculateLockoutStateRequest lastResetRequest;
    private CalculateLockoutStateRequest lastCalculateRequest;
    private VerificationAttempts resetAttemptsToReturn;
    private LockoutState stateToReturn;
    private LockoutPolicyParameters lastAddedParameters;
    private Collection<LockoutPolicyParameters> policyParametersToLoad;

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
    public Collection<LockoutPolicyParameters> loadPolicies() {
        return policyParametersToLoad;
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

    public void setResetAttemptsToReturn(final VerificationAttempts attempts) {
        resetAttemptsToReturn = attempts;
    }

    public void setStateToReturn(final LockoutState state) {
        this.stateToReturn = state;
    }

    public void setPoliciesToLoad(final Collection<LockoutPolicyParameters> policies) {
        this.policyParametersToLoad = policies;
    }

}
