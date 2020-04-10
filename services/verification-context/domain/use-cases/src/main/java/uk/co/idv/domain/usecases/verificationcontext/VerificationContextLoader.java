package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.time.Instant;
import java.util.UUID;

public interface VerificationContextLoader {

    VerificationContext load(UUID id);

    class VerificationContextNotFoundException extends RuntimeException {

        public VerificationContextNotFoundException(final UUID id) {
            super(String.format("verification context with id %s not found", id.toString()));
        }

    }

    class VerificationContextExpiredException extends RuntimeException {

        public VerificationContextExpiredException(final VerificationContext context, final Instant now) {
            super(String.format("context %s expired at %s current time is %s",
                    context.getId(),
                    context.getExpiry(),
                    now));
        }

    }

}
