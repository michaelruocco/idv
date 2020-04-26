package uk.co.idv.domain.usecases.lockout.attempt;

import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

public interface VerificationAttemptDao {

    void save(final VerificationAttempts attempts);

    Optional<VerificationAttempts> load(final UUID idvId);

}
