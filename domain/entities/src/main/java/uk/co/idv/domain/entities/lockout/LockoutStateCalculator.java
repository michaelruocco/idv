package uk.co.idv.domain.entities.lockout;

public interface LockoutStateCalculator {

    LockoutState calculate(final CalculateLockoutStateRequest request);

}
