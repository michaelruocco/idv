package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.LockoutState;

public interface LockoutFacade {

    LockoutState loadLockoutState(LockoutRequest request);

    LockoutState resetLockoutState(LockoutRequest request);

}
