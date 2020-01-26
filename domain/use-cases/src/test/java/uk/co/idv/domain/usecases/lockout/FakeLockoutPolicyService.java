package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.Collection;
import java.util.UUID;

public class FakeLockoutPolicyService implements LockoutPolicyService {

    private CalculateLockoutStateRequest lastResetRequest;
    private CalculateLockoutStateRequest lastCalculateRequest;
    private VerificationAttempts resetAttemptsToReturn;
    private LockoutState stateToReturn;
    private LockoutPolicy lastCreatedPolicy;
    private LockoutPolicy lastUpdatedPolicy;
    private Collection<LockoutPolicy> policiesToLoad;
    private LockoutPolicy policyToLoad;
    private UUID lastLoadedId;

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
    public void create(final LockoutPolicy policy) {
        this.lastCreatedPolicy = policy;
    }

    @Override
    public void update(final LockoutPolicy policy) {
        this.lastUpdatedPolicy = policy;
    }

    @Override
    public LockoutPolicy load(final UUID id) {
        lastLoadedId = id;
        return policyToLoad;
    }

    @Override
    public Collection<LockoutPolicy> loadAll() {
        return policiesToLoad;
    }

    public CalculateLockoutStateRequest getLastResetRequest() {
        return lastResetRequest;
    }

    public CalculateLockoutStateRequest getLastCalculateRequest() {
        return lastCalculateRequest;
    }

    public LockoutPolicy getLastCreatedPolicy() {
        return lastCreatedPolicy;
    }

    public LockoutPolicy getLastUpdatedPolicy() {
        return lastUpdatedPolicy;
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

    public void setPolicyToLoad(final LockoutPolicy policy) {
        this.policyToLoad = policy;
    }

    public UUID getLastLoadedId() {
        return lastLoadedId;
    }

}
