package uk.co.mruoc.idv.lockout.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.lockout.domain.service.CalculateLockoutStateRequest;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@ToString
public abstract class AbstractVerificationAttempt implements VerificationAttempt {

    private final UUID contextId;
    private final String channelId;
    private final String activityName;
    private final Alias alias;
    private final UUID idvIdValue;
    private final String methodName;
    private final UUID verificationId;
    private final Instant timestamp;
    private final boolean successful;

    @Override
    public String getAliasType() {
        return alias.getType();
    }

    @Override
    public CalculateLockoutStateRequest withAttempts(final VerificationAttempts attempts) {
        return CalculateLockoutStateRequest.builder()
                .channelId(channelId)
                .activityName(activityName)
                .alias(alias)
                .idvIdValue(idvIdValue)
                .timestamp(timestamp)
                .build();
    }

}
