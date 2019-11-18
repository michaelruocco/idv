package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.LockoutRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;

public interface LockoutFacade {

    LockoutState loadLockoutState(LockoutRequest request);

    LockoutState resetLockoutState(LockoutRequest request);

}
