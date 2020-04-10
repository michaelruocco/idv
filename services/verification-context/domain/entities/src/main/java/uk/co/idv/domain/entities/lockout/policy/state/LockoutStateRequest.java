package uk.co.idv.domain.entities.lockout.policy.state;

import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.time.Instant;
import java.util.UUID;

public interface LockoutStateRequest extends LockoutRequest {

    Instant getTimestamp();

    UUID getIdvIdValue();

}
