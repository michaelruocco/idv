package uk.co.mruoc.idv.verificationcontext.domain.service;

import lombok.Getter;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.UUID;

public interface VerificationContextService {

    VerificationContext create(final CreateContextRequest request);

    VerificationContext get(final GetContextRequest request);

    VerificationContext updateResults(final UpdateContextResultRequest request);

    class VerificationContextNotFoundException extends RuntimeException {

        public VerificationContextNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

    @Getter
    class LockedOutException extends RuntimeException {

        private final LockoutState lockoutState;

        public LockedOutException(final LockoutState lockoutState) {
            super("locked");
            this.lockoutState = lockoutState;
        }
    }

}
