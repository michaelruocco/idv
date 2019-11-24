package uk.co.idv.domain.entities.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;

import java.util.UUID;

public class SoftLockoutPolicy extends DefaultLockoutPolicy {

    private final SoftLockoutStateCalculator stateCalculator;

    public SoftLockoutPolicy(final UUID id,
                             final LockoutLevel level,
                             final RecordAttemptStrategy recordAttemptStrategy,
                             final SoftLockIntervals intervals) {
        this(id, level, recordAttemptStrategy, new SoftLockoutStateCalculator(intervals));
    }

    public SoftLockoutPolicy(final UUID id,
                             final LockoutLevel level,
                             final RecordAttemptStrategy recordAttemptStrategy,
                             final SoftLockoutStateCalculator stateCalculator) {
        super(id,
                stateCalculator,
                level,
                recordAttemptStrategy);
        this.stateCalculator = stateCalculator;
    }

    public SoftLockIntervals getIntervals() {
        return stateCalculator.getIntervals();
    }

}
