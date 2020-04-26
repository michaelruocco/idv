package uk.co.idv.domain.usecases.lockout.attempt;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

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
