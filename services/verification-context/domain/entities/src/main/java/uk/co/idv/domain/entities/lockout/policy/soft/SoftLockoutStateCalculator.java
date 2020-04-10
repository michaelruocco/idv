package uk.co.idv.domain.entities.lockout.policy.soft;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class SoftLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "soft-lock";

    private final SoftLockIntervals intervals;

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {} and intervals {}", this, request, intervals);
        final VerificationAttempts attempts = request.getAttempts();
        return intervals.findInterval(attempts.size())
                .map(interval -> toLockoutState(interval, request))
                .orElseGet(() -> new NotLockedState(attempts));
    }

    public SoftLockIntervals getIntervals() {
        return intervals;
    }

    private LockoutState toLockoutState(final SoftLockInterval interval, final CalculateLockoutStateRequest request) {
        final VerificationAttempts attempts = request.getAttempts();
        final Instant mostRecentTimestamp = attempts.getMostRecentTimestamp();
        final Duration duration = interval.getDuration();
        final Instant lockedUntil = mostRecentTimestamp.plus(duration);

        final boolean isLocked = request.getTimestamp().isBefore(lockedUntil);
        if (!isLocked) {
            return new NotLockedState(attempts);
        }

        return SoftLockoutState.builder()
                .attempts(attempts)
                .duration(duration)
                .lockedUntil(lockedUntil)
                .build();
    }

}
