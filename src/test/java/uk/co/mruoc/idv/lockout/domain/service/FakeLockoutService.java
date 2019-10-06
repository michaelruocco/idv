package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateValidator.LockedOutException;

public class FakeLockoutService implements LockoutService {

    private LockoutState stateToReturn;

    private RecordAttemptRequest lastRecordAttemptRequest;
    private LockoutStateRequest lastLoadStateRequest;
    private LockoutStateRequest lastValidateStateRequest;
    private LockoutStateRequest lastResetStateRequest;

    private boolean hasLockedState;

    @Override
    public LockoutState recordAttempt(final RecordAttemptRequest request) {
        this.lastRecordAttemptRequest = request;
        return stateToReturn;
    }

    @Override
    public LockoutState loadState(final LockoutStateRequest request) {
        this.lastLoadStateRequest = request;
        return stateToReturn;
    }

    @Override
    public LockoutState resetState(final LockoutStateRequest request) {
        this.lastResetStateRequest = request;
        return stateToReturn;
    }

    @Override
    public void validateState(final LockoutStateRequest request) {
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

    public LockoutStateRequest getLastLoadStateRequest() {
        return lastLoadStateRequest;
    }

    public LockoutStateRequest getLastValidateStateRequest() {
        return lastValidateStateRequest;
    }

    public LockoutStateRequest getLastResetStateRequest() {
        return lastResetStateRequest;
    }

    public void setHasLockedState(final LockoutState state) {
        setLockoutStateToReturn(state);
        hasLockedState = true;
    }

}
