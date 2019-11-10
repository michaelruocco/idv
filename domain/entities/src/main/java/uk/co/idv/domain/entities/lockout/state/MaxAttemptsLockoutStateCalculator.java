package uk.co.idv.domain.entities.lockout.state;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

@Slf4j
public class MaxAttemptsLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "max-attempts";

    private final int maxAttempts;

    public MaxAttemptsLockoutStateCalculator(final int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutStateMaxAttempts calculate(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {} and max attempts {}", this, request, maxAttempts);
        final VerificationAttempts attempts = request.getAttempts();
        return new LockoutStateMaxAttempts(attempts, maxAttempts);
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

}
