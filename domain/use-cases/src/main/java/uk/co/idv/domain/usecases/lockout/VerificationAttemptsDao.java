package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

public interface VerificationAttemptsDao {

    void save(final VerificationAttempts attempts);

    Optional<VerificationAttempts> load(final UUID idvId);

}
