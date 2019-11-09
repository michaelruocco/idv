package uk.co.idv.domain.entities.lockout;


public interface LockoutPolicy {

    boolean appliesTo(final LockoutRequest request);

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

    VerificationAttempts reset(final VerificationAttempts attempts, final LockoutRequest request);

    LockoutState reset(final CalculateLockoutStateRequest request);

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

    LockoutPolicyParameters getParameters();

    LockoutStateCalculator getStateCalculator();

    RecordAttemptStrategy getRecordAttemptStrategy();

}
