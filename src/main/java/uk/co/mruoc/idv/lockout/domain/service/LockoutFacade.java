package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public interface LockoutFacade {

    LockoutState getLockoutState(LockoutRequest request);

    LockoutState resetLockoutState(LockoutRequest request);

}
