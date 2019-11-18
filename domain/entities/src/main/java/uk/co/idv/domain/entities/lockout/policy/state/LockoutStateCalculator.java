package uk.co.idv.domain.entities.lockout.policy.state;

public interface LockoutStateCalculator {

    String getType();

    LockoutState calculate(final CalculateLockoutStateRequest request);

}
