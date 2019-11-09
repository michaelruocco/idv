package uk.co.idv.domain.entities.lockout;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.usecases.lockout.CalculateLockoutStateRequest;

@Slf4j
public class MaxAttemptsLockoutStateCalculator implements LockoutStateCalculator {

    private final int maxAttempts;

    public MaxAttemptsLockoutStateCalculator(final int maxAttempts) {
        this.maxAttempts = maxAttempts;
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
