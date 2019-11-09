package uk.co.idv.domain.usecases.lockout;

import java.time.Instant;
import java.util.UUID;

public interface LockoutStateRequest extends LockoutRequest {

    Instant getTimestamp();

    UUID getIdvIdValue();

}
