package uk.co.idv.domain.entities.lockout;

import java.util.UUID;

public interface LockoutState {

    UUID getId();

    UUID getIdvId();

    boolean isLocked();

    VerificationAttempts getAttempts();

    String getMessage();

}
