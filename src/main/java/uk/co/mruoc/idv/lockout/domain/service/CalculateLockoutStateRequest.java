package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

public interface CalculateLockoutStateRequest {
    String getChannelId();

    String getActivityName();

    Instant getTimestamp();

    Alias getAlias();

    UUID getIdvIdValue();

    VerificationAttempts getAttempts();
}
