package uk.co.idv.domain.entities.lockout.policy.nonlocking;

import uk.co.idv.domain.entities.lockout.policy.DefaultLockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutLevel;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordNever;

import java.util.UUID;

public class NonLockingLockoutPolicy extends DefaultLockoutPolicy {

    public NonLockingLockoutPolicy(final UUID id,
                                   final LockoutLevel level) {
        this(id, level, new RecordNever(), new NonLockingLockoutStateCalculator());
    }

    public NonLockingLockoutPolicy(final UUID id,
                                   final LockoutLevel level,
                                   final RecordAttemptStrategy recordAttemptStrategy,
                                   final NonLockingLockoutStateCalculator stateCalculator) {
        super(id,
                stateCalculator,
                level,
                recordAttemptStrategy);
    }

}
