package uk.co.idv.domain.entities.lockout.policy.nonlocking;

import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

@Slf4j
@EqualsAndHashCode
public class NonLockingLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "non-locking";

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {}", this, request);
        final VerificationAttempts attempts = request.getAttempts();
        return new NonLockingLockoutState(attempts);
    }

}
