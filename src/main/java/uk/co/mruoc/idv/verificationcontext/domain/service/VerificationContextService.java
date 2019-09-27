package uk.co.mruoc.idv.verificationcontext.domain.service;

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

}
