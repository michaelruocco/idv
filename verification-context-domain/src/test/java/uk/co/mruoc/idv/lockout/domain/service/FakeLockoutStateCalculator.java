package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public class FakeLockoutStateCalculator implements LockoutStateCalculator {

    private CalculateLockoutStateRequest lastCalculateStateRequest;
    private LockoutState stateToReturn;

    @Override
    public LockoutState calculate(final CalculateLockoutStateRequest request) {
        this.lastCalculateStateRequest = request;
        return stateToReturn;
    }

    public CalculateLockoutStateRequest getLastCalculateStateRequest() {
        return lastCalculateStateRequest;
    }

    public void setLockoutStateToReturn(final LockoutState stateToReturn) {
        this.stateToReturn = stateToReturn;
    }

}
