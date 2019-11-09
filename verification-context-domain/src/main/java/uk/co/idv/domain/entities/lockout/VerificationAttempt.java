package uk.co.idv.domain.entities.lockout;

import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.usecases.lockout.LockoutStateRequest;

import java.time.Instant;
import java.util.UUID;

public interface VerificationAttempt extends LockoutStateRequest {

    UUID getContextId();

    UUID getIdvIdValue();

    String getMethodName();

    boolean isSuccessful();

    UUID getVerificationId();

    Instant getTimestamp();

    Alias getAlias();

}
