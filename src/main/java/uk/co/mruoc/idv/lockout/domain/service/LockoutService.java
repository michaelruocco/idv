package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Getter;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;

public interface LockoutService {

    LockoutState recordAttempt(final RecordAttemptRequest request);

    LockoutState loadState(final LoadLockoutStateRequest request);

    void validateState(final LoadLockoutStateRequest request);

    @Getter
    class LockedOutException extends RuntimeException {

        private final LockoutState lockoutState;

        public LockedOutException(final LockoutState lockoutState) {
            super("locked");
            this.lockoutState = lockoutState;
        }
    }

}
