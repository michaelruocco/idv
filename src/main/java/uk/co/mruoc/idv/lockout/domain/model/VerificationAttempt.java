package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequest;

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
