package uk.co.idv.domain.entities.lockout.state;

public interface LockoutStateCalculator {

    LockoutState calculate(final CalculateLockoutStateRequest request);

}
