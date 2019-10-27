package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

public interface LockoutPolicy {

    boolean appliesTo(final LockoutRequest request);

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

    VerificationAttempts reset(final VerificationAttempts attempts);

    LockoutState reset(final CalculateLockoutStateRequest request);

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

    LockoutPolicyParameters getParameters();

}
