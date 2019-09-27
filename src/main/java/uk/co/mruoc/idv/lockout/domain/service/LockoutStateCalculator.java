package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public interface LockoutStateCalculator {

    LockoutState calculate(final CalculateLockoutStateRequest request);
}
