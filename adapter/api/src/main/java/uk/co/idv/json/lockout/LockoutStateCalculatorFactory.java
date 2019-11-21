package uk.co.idv.json.lockout;

import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;

public class LockoutStateCalculatorFactory {

    public LockoutStateCalculator build(final LockoutPolicyParameters parameters) {
        final String type = parameters.getLockoutType();
        if (HardLockoutStateCalculator.TYPE.equals(type)) {
            return toHardLockoutStateCalculator((HardLockoutPolicyParameters) parameters);
        }
        throw new LockoutTypeNotSupportedException(type);
    }

    private static LockoutStateCalculator toHardLockoutStateCalculator(final HardLockoutPolicyParameters parameters) {
        return new HardLockoutStateCalculator(parameters.getMaxNumberOfAttempts());
    }

}
