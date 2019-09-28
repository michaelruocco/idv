package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

@Builder
public class LockoutStateValidator {

    private final LockoutStateLoader stateLoader;

    public boolean validateState(final LoadLockoutStateRequest request) {
        final LockoutState lockoutState = stateLoader.load(request);
        if (lockoutState.isLocked()) {
            throw new LockedOutException(lockoutState);
        }
        return true;
    }

    @Getter
    public static class LockedOutException extends RuntimeException {

        private final LockoutState lockoutState;

        public LockedOutException(final LockoutState lockoutState) {
            super("locked");
            this.lockoutState = lockoutState;
        }
    }

}
