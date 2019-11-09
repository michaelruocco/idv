package uk.co.idv.domain.usecases.verificationcontext;

import uk.co.idv.domain.entities.verificationcontext.VerificationContext;

import java.util.Optional;
import java.util.UUID;

public interface VerificationContextDao {

    void save(final VerificationContext context);

    Optional<VerificationContext> load(final UUID id);

}
