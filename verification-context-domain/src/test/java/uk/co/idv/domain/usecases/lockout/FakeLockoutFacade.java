package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.LockoutState;

public class FakeLockoutFacade implements LockoutFacade {

    private LockoutRequest lastLoadRequest;
    private LockoutRequest lastResetRequest;
    private LockoutState stateToReturn;

    @Override
    public LockoutState loadLockoutState(final LockoutRequest request) {
        this.lastLoadRequest = request;
        return stateToReturn;
    }

    @Override
    public LockoutState resetLockoutState(final LockoutRequest request) {
        this.lastResetRequest = request;
        return stateToReturn;
    }

    public LockoutRequest getLastLoadRequest() {
        return lastLoadRequest;
    }

    public LockoutRequest getLastResetRequest() {
        return lastResetRequest;
    }

    public void setStateToReturn(final LockoutState state) {
        this.stateToReturn = state;
    }

}
