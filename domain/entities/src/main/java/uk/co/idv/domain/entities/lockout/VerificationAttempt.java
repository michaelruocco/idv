package uk.co.idv.domain.entities.lockout;

import java.time.Instant;
import java.util.UUID;
import uk.co.idv.domain.entities.identity.Alias;

public interface VerificationAttempt extends LockoutStateRequest {

    UUID getContextId();

    UUID getIdvIdValue();

    String getMethodName();

    boolean isSuccessful();

    UUID getVerificationId();

    Instant getTimestamp();

    Alias getAlias();

}
