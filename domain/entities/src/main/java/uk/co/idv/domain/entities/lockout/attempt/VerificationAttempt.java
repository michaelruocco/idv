package uk.co.idv.domain.entities.lockout.attempt;

import java.time.Instant;
import java.util.UUID;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.state.LockoutStateRequest;

public interface VerificationAttempt extends LockoutStateRequest {

    UUID getContextId();

    UUID getIdvIdValue();

    String getMethodName();

    boolean isSuccessful();

    UUID getVerificationId();

    Instant getTimestamp();

    Alias getAlias();

}
