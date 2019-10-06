package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public interface LockoutPolicy {

    boolean appliesTo(final LockoutRequest request);

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

    LockoutState reset(final CalculateLockoutStateRequest request);

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

}
