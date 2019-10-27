package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.lockout.domain.service.CalculateLockoutStateRequest;

public interface LockoutStateCalculator {

    LockoutState calculate(final CalculateLockoutStateRequest request);

}
