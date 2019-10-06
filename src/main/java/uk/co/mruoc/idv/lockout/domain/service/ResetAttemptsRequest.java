package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.Getter;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public class ResetAttemptsRequest implements LockoutStateRequest {

    private final String channelId;
    private final String activityName;
    private final Alias alias;
    private final Instant timestamp;
    private final UUID idvIdValue;
    private final VerificationAttempts attempts;


    @Override
    public String getAliasType() {
        return alias.getType();
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public UUID getIdvIdValue() {
        return idvIdValue;
    }

}
