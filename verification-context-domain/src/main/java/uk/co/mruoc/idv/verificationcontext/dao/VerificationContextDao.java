package uk.co.mruoc.idv.verificationcontext.dao;

import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationContext;

import java.util.Optional;
import java.util.UUID;

public interface VerificationContextDao {

    void save(final VerificationContext context);

    Optional<VerificationContext> load(final UUID id);

}
