package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

@Builder
public class LockoutStateValidator {

    private final LockoutStateLoader stateLoader;

    public void validateState(final LoadLockoutStateRequest request) {
        final LockoutState lockoutState = stateLoader.load(request);
        if (lockoutState.isLocked()) {
            throw new LockoutService.LockedOutException(lockoutState);
        }
    }

}
