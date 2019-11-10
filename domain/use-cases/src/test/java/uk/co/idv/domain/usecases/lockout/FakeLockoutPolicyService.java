package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.Collection;

public class FakeLockoutPolicyService implements LockoutPolicyService {

    private CalculateLockoutStateRequest lastResetRequest;
    private CalculateLockoutStateRequest lastCalculateRequest;
    private VerificationAttempts resetAttemptsToReturn;
    private LockoutState stateToReturn;
    private LockoutPolicy lastAddedPolicy;
    private Collection<LockoutPolicy> policiesToLoad;

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
    public void savePolicy(final LockoutPolicy policy) {
        this.lastAddedPolicy = policy;
    }

    @Override
    public Collection<LockoutPolicy> loadPolicies() {
        return policiesToLoad;
    }

    public CalculateLockoutStateRequest getLastResetRequest() {
        return lastResetRequest;
    }

    public CalculateLockoutStateRequest getLastCalculateRequest() {
        return lastCalculateRequest;
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

    public void setPoliciesToLoad(final Collection<LockoutPolicy> policies) {
        this.policiesToLoad = policies;
    }

}
