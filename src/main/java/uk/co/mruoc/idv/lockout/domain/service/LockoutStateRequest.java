package uk.co.mruoc.idv.lockout.domain.service;

import java.time.Instant;
import java.util.UUID;

public interface LockoutStateRequest extends LockoutRequest {

    Instant getTimestamp();

    UUID getIdvIdValue();

}
