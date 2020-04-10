package uk.co.idv.domain.entities.lockout.policy.state;

import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.util.UUID;

public interface LockoutState {

    UUID getId();

    UUID getIdvId();

    boolean isLocked();

    VerificationAttempts getAttempts();

    String getMessage();

}
