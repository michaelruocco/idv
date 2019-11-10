package uk.co.idv.domain.entities.lockout.state;

import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.MaxAttemptsLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;

public class LockoutStateCalculatorFactory {

    public LockoutStateCalculator build(final LockoutPolicyParameters parameters) {
        final String type = parameters.getLockoutType();
        if (MaxAttemptsLockoutPolicyParameters.TYPE.equals(type)) {
            return toMaxAttemptsStateCalculator((MaxAttemptsLockoutPolicyParameters) parameters);
        }
        throw new LockoutTypeNotSupportedException(type);
    }

    private static LockoutStateCalculator toMaxAttemptsStateCalculator(final MaxAttemptsLockoutPolicyParameters parameters) {
        return new MaxAttemptsLockoutStateCalculator(parameters.getMaxNumberOfAttempts());
    }

}
