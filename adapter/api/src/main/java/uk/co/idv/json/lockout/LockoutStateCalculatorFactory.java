package uk.co.idv.json.lockout;

import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.maxattempts.MaxAttemptsLockoutStateCalculator;

public class LockoutStateCalculatorFactory {

    public LockoutStateCalculator build(final LockoutPolicyParameters parameters) {
        final String type = parameters.getLockoutType();
        if (MaxAttemptsLockoutStateCalculator.TYPE.equals(type)) {
            return toMaxAttemptsStateCalculator((MaxAttemptsLockoutPolicyParameters) parameters);
        }
        throw new LockoutTypeNotSupportedException(type);
    }

    private static LockoutStateCalculator toMaxAttemptsStateCalculator(final MaxAttemptsLockoutPolicyParameters parameters) {
        return new MaxAttemptsLockoutStateCalculator(parameters.getMaxNumberOfAttempts());
    }

}
