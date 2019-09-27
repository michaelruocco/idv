package uk.co.mruoc.idv.lockout.domain.model;

import java.util.UUID;

public interface LockoutState {

    UUID getId();

    UUID getIdvId();

    LockoutType getType();

    boolean isLocked();

    VerificationAttempts getAttempts();

}
