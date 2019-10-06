package uk.co.mruoc.idv.lockout.domain.model;

import java.util.UUID;

public interface LockoutState {

    UUID getId();

    UUID getIdvId();

    boolean isLocked();

    VerificationAttempts getAttempts();

    String getMessage();

}
