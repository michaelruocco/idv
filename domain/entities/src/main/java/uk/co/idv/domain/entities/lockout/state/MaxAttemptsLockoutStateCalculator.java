package uk.co.idv.domain.entities.lockout.state;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

@Slf4j
public class MaxAttemptsLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "max-attempts";

    private final int maxNumberOfAttempts;

    public MaxAttemptsLockoutStateCalculator(final int maxNumberOfAttempts) {
        this.maxNumberOfAttempts = maxNumberOfAttempts;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutStateMaxAttempts calculate(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {} and max number of attempts {}", this, request, maxNumberOfAttempts);
        final VerificationAttempts attempts = request.getAttempts();
        return new LockoutStateMaxAttempts(attempts, maxNumberOfAttempts);
    }

    public int getMaxNumberOfAttempts() {
        return maxNumberOfAttempts;
    }

}
