package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.UUID;

public interface VerificationAttemptsLoader {

    VerificationAttempts load(UUID idvId);

}
