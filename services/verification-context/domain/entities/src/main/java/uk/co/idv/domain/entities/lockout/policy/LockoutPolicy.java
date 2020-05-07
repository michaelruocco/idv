package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptRequest;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

public interface LockoutPolicy extends Policy {

    boolean shouldRecordAttempt(final RecordAttemptRequest request);

    VerificationAttempts reset(final CalculateLockoutStateRequest request);

    LockoutState calculateState(final CalculateLockoutStateRequest request);

    String getType();

    String getRecordAttemptStrategyType();

    String getChannelId();

    LockoutStateCalculator getStateCalculator();

    RecordAttemptStrategy getRecordAttemptStrategy();

}
