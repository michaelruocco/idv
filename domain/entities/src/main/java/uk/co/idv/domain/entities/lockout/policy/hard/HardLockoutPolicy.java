package uk.co.idv.domain.entities.lockout.policy.hard;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;

import java.util.UUID;

public class HardLockoutPolicy extends DefaultLockoutPolicy {

    private final HardLockoutStateCalculator stateCalculator;

    public HardLockoutPolicy(final UUID id,
                             final LockoutLevel level,
                             final RecordAttemptStrategy recordAttemptStrategy,
                             final int maxNumberOfAttempts) {
        this(id, level, recordAttemptStrategy, new HardLockoutStateCalculator(maxNumberOfAttempts));
    }

    public HardLockoutPolicy(final UUID id,
                             final LockoutLevel level,
                             final RecordAttemptStrategy recordAttemptStrategy,
                             final HardLockoutStateCalculator stateCalculator) {
        super(id,
                stateCalculator,
                level,
                recordAttemptStrategy);
        this.stateCalculator = stateCalculator;
    }

    public int getMaxNumberOfAttempts() {
        return stateCalculator.getMaxNumberOfAttempts();
    }

}
