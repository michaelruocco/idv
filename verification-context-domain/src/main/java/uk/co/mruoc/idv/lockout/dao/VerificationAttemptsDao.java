package uk.co.mruoc.idv.lockout.dao;

import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.Optional;
import java.util.UUID;

public interface VerificationAttemptsDao {

    void save(final VerificationAttempts attempts);

    Optional<VerificationAttempts> load(final UUID idvId);

}
