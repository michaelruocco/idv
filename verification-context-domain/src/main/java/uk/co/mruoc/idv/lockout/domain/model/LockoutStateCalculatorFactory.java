package uk.co.mruoc.idv.lockout.domain.model;

public class LockoutStateCalculatorFactory {

    public LockoutStateCalculator build(final AbstractLockoutPolicyParameters parameters) {
        final String type = parameters.getLockoutType();
        if (MaxAttemptsLockoutPolicyParameters.TYPE.equals(type)) {
            return toMaxAttemptsStateCalculator((MaxAttemptsLockoutPolicyParameters) parameters);
        }
        throw new LockoutTypeNotSupportedException(type);
    }

    private static LockoutStateCalculator toMaxAttemptsStateCalculator(final MaxAttemptsLockoutPolicyParameters parameters) {
        return new MaxAttemptsLockoutStateCalculator(parameters.getMaxNumberOfAttempts());
    }

    public static class LockoutTypeNotSupportedException extends RuntimeException {

        public LockoutTypeNotSupportedException(final String type) {
            super(type);
        }

    }

}
