package uk.co.mruoc.idv.verificationcontext.domain.service;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.time.Instant;
import java.util.UUID;

public interface VerificationContextLoader {

    VerificationContext load(LoadContextRequest request);

    class VerificationContextNotFoundException extends RuntimeException {

        public VerificationContextNotFoundException(final UUID id) {
            super(id.toString());
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
