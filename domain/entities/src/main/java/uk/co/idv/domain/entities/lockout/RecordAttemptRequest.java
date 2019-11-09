package uk.co.idv.domain.entities.lockout;

import lombok.Builder;
import lombok.Getter;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.identity.Alias;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
public class RecordAttemptRequest implements LockoutStateRequest {

    private final VerificationResult result;
    private final VerificationContext context;

    @Override
    public Alias getAlias() {
        return context.getProvidedAlias();
    }

    @Override
    public Instant getTimestamp() {
        return result.getTimestamp();
    }

    @Override
    public UUID getIdvIdValue() {
        return context.getIdvIdValue();
    }

    @Override
    public String getActivityName() {
        return context.getActivityName();
    }

    @Override
    public String getAliasType() {
        final Alias alias = context.getProvidedAlias();
        return alias.getType();
    }

    @Override
    public String getChannelId() {
        return context.getChannelId();
    }

}
