package uk.co.idv.domain.entities.lockout.policy.hard;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;

@Slf4j
public class HardLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "hard-lock";

    private final int maxNumberOfAttempts;

    public HardLockoutStateCalculator(final int maxNumberOfAttempts) {
        this.maxNumberOfAttempts = maxNumberOfAttempts;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public HardLockoutState calculate(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {} and max number of attempts {}", this, request, maxNumberOfAttempts);
        final VerificationAttempts attempts = request.getAttempts();
        return new HardLockoutState(attempts, maxNumberOfAttempts);
    }

    public int getMaxNumberOfAttempts() {
        return maxNumberOfAttempts;
    }

}
