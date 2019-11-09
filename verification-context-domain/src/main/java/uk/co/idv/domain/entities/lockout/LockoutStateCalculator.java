package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.usecases.lockout.CalculateLockoutStateRequest;

public interface LockoutStateCalculator {

    LockoutState calculate(final CalculateLockoutStateRequest request);

}
