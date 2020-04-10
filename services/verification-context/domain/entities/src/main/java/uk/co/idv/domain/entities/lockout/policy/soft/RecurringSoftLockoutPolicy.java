package uk.co.idv.domain.entities.lockout.policy.soft;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;

import java.util.UUID;

public class RecurringSoftLockoutPolicy extends DefaultLockoutPolicy {

    private final RecurringSoftLockoutStateCalculator stateCalculator;

    public RecurringSoftLockoutPolicy(final UUID id,
                                      final LockoutLevel level,
                                      final RecordAttemptStrategy recordAttemptStrategy,
                                      final SoftLockInterval interval) {
        this(id, level, recordAttemptStrategy, new RecurringSoftLockoutStateCalculator(interval));
    }

    public RecurringSoftLockoutPolicy(final UUID id,
                                      final LockoutLevel level,
                                      final RecordAttemptStrategy recordAttemptStrategy,
                                      final RecurringSoftLockoutStateCalculator stateCalculator) {
        super(id,
                stateCalculator,
                level,
                recordAttemptStrategy);
        this.stateCalculator = stateCalculator;
    }

    public SoftLockInterval getInterval() {
        return stateCalculator.getInterval();
    }

}
