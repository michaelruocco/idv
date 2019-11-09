package uk.co.idv.domain.usecases.lockout;

import uk.co.idv.domain.entities.lockout.VerificationAttempts;

public class LockoutStateRequestConverter {

    public CalculateLockoutStateRequest toCalculateRequest(final LockoutStateRequest request,
                                                           final VerificationAttempts attempts) {
        return CalculateLockoutStateRequest.builder()
                .channelId(request.getChannelId())
                .activityName(request.getActivityName())
                .alias(request.getAlias())
                .timestamp(request.getTimestamp())
                .idvIdValue(request.getIdvIdValue())
                .attempts(attempts)
                .build();
    }

}
