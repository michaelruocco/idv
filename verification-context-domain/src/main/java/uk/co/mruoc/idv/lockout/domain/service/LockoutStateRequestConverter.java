package uk.co.mruoc.idv.lockout.domain.service;

import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

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
