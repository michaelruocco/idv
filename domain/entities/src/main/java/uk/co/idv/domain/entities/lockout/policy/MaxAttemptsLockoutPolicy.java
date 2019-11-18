package uk.co.idv.domain.entities.lockout.policy;

import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategy;
import uk.co.idv.domain.entities.lockout.state.MaxAttemptsLockoutStateCalculator;

import java.util.UUID;

public class MaxAttemptsLockoutPolicy extends DefaultLockoutPolicy {

    private final MaxAttemptsLockoutStateCalculator stateCalculator;

    public MaxAttemptsLockoutPolicy(final UUID id,
                                    final LockoutLevel level,
                                    final RecordAttemptStrategy recordAttemptStrategy,
                                    final int maxNumberOfAttempts) {
        this(id, level, recordAttemptStrategy, new MaxAttemptsLockoutStateCalculator(maxNumberOfAttempts));
    }

    public MaxAttemptsLockoutPolicy(final UUID id,
                                    final LockoutLevel level,
                                    final RecordAttemptStrategy recordAttemptStrategy,
                                    final MaxAttemptsLockoutStateCalculator stateCalculator) {
        super(id,
                stateCalculator,
                level,
                recordAttemptStrategy);
        this.stateCalculator = stateCalculator;
    }

    public int getMaxAttempts() {
        return stateCalculator.getMaxNumberOfAttempts();
    }

}
