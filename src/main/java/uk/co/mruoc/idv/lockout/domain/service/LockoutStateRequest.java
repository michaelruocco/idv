package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

public interface LockoutStateRequest extends LockoutRequest {

    Instant getTimestamp();

    UUID getIdvIdValue();

    CalculateLockoutStateRequest withAttempts(VerificationAttempts attempts);

}
