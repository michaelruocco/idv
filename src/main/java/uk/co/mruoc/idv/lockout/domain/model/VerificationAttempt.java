package uk.co.mruoc.idv.lockout.domain.model;

import uk.co.mruoc.idv.identity.domain.model.Alias;

import java.time.Instant;
import java.util.UUID;

public interface VerificationAttempt {

    UUID getContextId();

    String getChannelId();

    String getActivityName();

    Alias getProvidedAlias();

    UUID getIdvIdValue();

    String getMethodName();

    boolean isSuccessful();

    UUID getVerificationId();

    Instant getTimestamp();

}
