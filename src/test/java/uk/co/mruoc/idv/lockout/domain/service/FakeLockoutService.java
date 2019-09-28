package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public class FakeLockoutService implements LockoutService {

    private LockoutState stateToReturn;

    private RecordAttemptRequest lastRecordAttemptRequest;
    private LoadLockoutStateRequest lastLoadStateRequest;
    private LoadLockoutStateRequest lastValidateStateRequest;

    private boolean hasLockedState ;

    @Override
    public LockoutState recordAttempt(final RecordAttemptRequest request) {
        this.lastRecordAttemptRequest = request;
        return stateToReturn;
    }

    @Override
    public LockoutState loadState(final LoadLockoutStateRequest request) {
        this.lastLoadStateRequest = request;
        return stateToReturn;
    }

    @Override
    public void validateState(final LoadLockoutStateRequest request) {
        this.lastValidateStateRequest = request;
        if (hasLockedState) {
            throw new LockedOutException(stateToReturn);
        }
    }

    public void setLockoutStateToReturn(final LockoutState state) {
        this.stateToReturn = state;
    }

    public RecordAttemptRequest getLastRecordAttemptRequest() {
        return lastRecordAttemptRequest;
    }

    public LoadLockoutStateRequest getLastLoadStateRequest() {
        return lastLoadStateRequest;
    }

    public LoadLockoutStateRequest getLastValidateStateRequest() {
        return lastValidateStateRequest;
    }

    public void setHasLockedState(final LockoutState state) {
        setLockoutStateToReturn(state);
        hasLockedState = true;
    }

}
