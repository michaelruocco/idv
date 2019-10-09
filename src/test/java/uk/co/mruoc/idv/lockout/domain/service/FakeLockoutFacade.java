package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public class FakeLockoutFacade implements LockoutFacade {

    private LockoutRequest lastGetRequest;
    private LockoutRequest lastResetRequest;
    private LockoutState stateToReturn;

    @Override
    public LockoutState getLockoutState(final LockoutRequest request) {
        this.lastGetRequest = request;
        return stateToReturn;
    }

    @Override
    public LockoutState resetLockoutState(final LockoutRequest request) {
        this.lastResetRequest = request;
        return stateToReturn;
    }

    public LockoutRequest getLastGetRequest() {
        return lastGetRequest;
    }

    public LockoutRequest getLastResetRequest() {
        return lastResetRequest;
    }

    public void setStateToReturn(final LockoutState state) {
        this.stateToReturn = state;
    }

}
