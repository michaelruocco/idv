package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.identity.domain.model.Alias;

import java.time.Instant;
import java.util.UUID;

public interface LoadLockoutStateRequest extends LockoutPolicyRequest {

    Alias getAlias();

    Instant getTimestamp();

    UUID getIdvIdValue();

}
