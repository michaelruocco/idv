package uk.co.idv.domain.entities.lockout.policy;


import uk.co.idv.domain.entities.lockout.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.UUID;

public interface LockoutPolicy {

    UUID getId();

    String getLockoutType();

    String getLockoutLevelType();

    String getRecordAttemptStrategyType();

    boolean appliesTo(final LockoutRequest request);

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

    VerificationAttempts reset(final VerificationAttempts attempts, final LockoutRequest request);

    LockoutState reset(final CalculateLockoutStateRequest request);

    LockoutState calculateLockoutState(final CalculateLockoutStateRequest request);

    LockoutStateCalculator getStateCalculator();

    RecordAttemptStrategy getRecordAttemptStrategy();

    LockoutLevel getLockoutLevel();

}
