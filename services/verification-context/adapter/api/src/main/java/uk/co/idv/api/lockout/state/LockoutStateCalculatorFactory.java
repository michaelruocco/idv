package uk.co.idv.api.lockout.state;

import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateCalculator;
import uk.co.idv.domain.entities.lockout.policy.hard.HardLockoutStateCalculator;
import uk.co.idv.api.lockout.policy.hard.HardLockoutPolicyAttributes;
import uk.co.idv.api.lockout.policy.LockoutPolicyAttributes;

public class LockoutStateCalculatorFactory {

    public LockoutStateCalculator build(final LockoutPolicyAttributes parameters) {
        final String type = parameters.getType();
        if (HardLockoutStateCalculator.TYPE.equals(type)) {
            return toHardLockoutStateCalculator((HardLockoutPolicyAttributes) parameters);
        }
        throw new LockoutTypeNotSupportedException(type);
    }

    private static LockoutStateCalculator toHardLockoutStateCalculator(final HardLockoutPolicyAttributes parameters) {
        return new HardLockoutStateCalculator(parameters.getMaxNumberOfAttempts());
    }

}
