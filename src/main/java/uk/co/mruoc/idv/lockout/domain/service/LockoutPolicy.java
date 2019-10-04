package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

public interface LockoutPolicy {

    boolean appliesTo(final LockoutPolicyRequest request);

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

    VerificationAttempts reset(final ResetAttemptsRequest request);

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

    class LockoutPolicyException extends RuntimeException {

        public LockoutPolicyException(final String message) {
            super(message);
        }

    }

}
