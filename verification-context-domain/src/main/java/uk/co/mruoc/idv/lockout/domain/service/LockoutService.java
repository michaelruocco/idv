package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public interface LockoutService {

    LockoutState recordAttempt(final RecordAttemptRequest request);

    LockoutState loadState(final LockoutStateRequest request);

    LockoutState resetState(final LockoutStateRequest request);

    void validateState(final LockoutStateRequest request);

}
