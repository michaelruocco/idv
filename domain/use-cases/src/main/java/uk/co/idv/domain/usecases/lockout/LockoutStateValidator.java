package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.lockout.state.LockoutState;
import uk.co.idv.domain.entities.lockout.state.LockoutStateRequest;

@Builder
public class LockoutStateValidator {

    private final LockoutStateLoader stateLoader;

    public boolean validateState(final LockoutStateRequest request) {
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
