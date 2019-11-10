package uk.co.idv.domain.entities.lockout.state;

import uk.co.idv.domain.entities.lockout.policy.MaxAttemptsAliasLevelLockoutPolicyParameters;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyParameters;

public class LockoutStateCalculatorFactory {

    public LockoutStateCalculator build(final LockoutPolicyParameters parameters) {
        final String type = parameters.getLockoutType();
        if (MaxAttemptsAliasLevelLockoutPolicyParameters.TYPE.equals(type)) {
            return toMaxAttemptsStateCalculator((MaxAttemptsAliasLevelLockoutPolicyParameters) parameters);
        }
        throw new LockoutTypeNotSupportedException(type);
    }

    private static LockoutStateCalculator toMaxAttemptsStateCalculator(final MaxAttemptsAliasLevelLockoutPolicyParameters parameters) {
        return new MaxAttemptsLockoutStateCalculator(parameters.getMaxNumberOfAttempts());
    }

    public static class LockoutTypeNotSupportedException extends RuntimeException {

        public LockoutTypeNotSupportedException(final String type) {
            super(type);
        }

    }

}
