package uk.co.idv.domain.entities.lockout.policy.soft;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.state.NotLockedState;

@Slf4j
@RequiredArgsConstructor
@EqualsAndHashCode
public class SoftLockoutStateCalculator implements LockoutStateCalculator {

    public static final String TYPE = "soft-lock";

    private final SoftLockIntervals intervals;
    private final SoftLockoutStateFactory stateFactory;

    public SoftLockoutStateCalculator(final SoftLockIntervals intervals) {
        this(intervals, new SoftLockoutStateFactory());
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public LockoutState calculate(final CalculateLockoutStateRequest request) {
        log.info("calculating lock from calculator {} with request {} and intervals {}", this, request, intervals);
        final VerificationAttempts attempts = request.getAttempts();
        return intervals.findInterval(attempts.size())
                .map(interval -> stateFactory.build(interval.getDuration(), request))
                .orElseGet(() -> new NotLockedState(attempts));
    }

    public SoftLockIntervals getIntervals() {
        return intervals;
    }

}
