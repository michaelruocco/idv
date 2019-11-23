package uk.co.idv.domain.entities.lockout.policy.soft;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.state.NotLockedState;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
public class RecurringSoftLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "recurring-soft-lock";

    private final SoftLockInterval interval;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {} and interval {}", this, request, interval);
        final VerificationAttempts attempts = request.getAttempts();
        final boolean locked = isLocked(attempts);
        if (locked) {
            return toLockoutState(attempts);
        }
        return new NotLockedState(attempts);
    }

    private boolean isLocked(final VerificationAttempts attempts) {
        return attempts.size() % interval.getNumberOfAttempts() == 0;
    }

    private LockoutState toLockoutState(final VerificationAttempts attempts) {
        final Instant mostRecentTimestamp = attempts.getMostRecentTimestamp();
        final Duration duration = interval.getDuration();
        final Instant lockedUntil = mostRecentTimestamp.plus(duration);
        return SoftLockoutState.builder()
                .attempts(attempts)
                .duration(duration)
                .lockedUntil(lockedUntil)
                .build();
    }

}
