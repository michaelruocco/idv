package uk.co.mruoc.idv.verificationcontext.dao;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.UUID;

public interface VerificationContextDao {

    void save(final VerificationContext context);

    VerificationContext load(final UUID id);

    class VerificationContextNotFoundException extends RuntimeException {

        public VerificationContextNotFoundException(final UUID id) {
            super(id.toString());
        }

    }

}
