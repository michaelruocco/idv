package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public interface LockoutService {

    LockoutState recordAttempt(final RecordAttemptRequest request);

    LockoutState loadState(final LoadLockoutStateRequest request);

    LockoutState resetState(final LoadLockoutStateRequest request);

    void validateState(final LoadLockoutStateRequest request);

}
