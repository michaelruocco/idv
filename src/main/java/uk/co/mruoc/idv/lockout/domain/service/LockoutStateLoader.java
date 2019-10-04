package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;


@Builder
public class LockoutStateLoader {

    private final VerificationAttemptsLoader attemptsLoader;
    private final LockoutPolicyLoader policyLoader;

    public LockoutState load(final LoadLockoutStateRequest request) {
        final VerificationAttempts attempts = attemptsLoader.load(request.getIdvIdValue());
        return calculateLockoutState(request, attempts);
    }

    private LockoutState calculateLockoutState(final LoadLockoutStateRequest loadStateRequest,
                                               final VerificationAttempts attempts) {
        final CalculateLockoutStateRequest request = CalculateLockoutStateRequest.builder()
                .channelId(loadStateRequest.getChannelId())
                .activityName(loadStateRequest.getActivityName())
                .alias(loadStateRequest.getAlias())
                .timestamp(loadStateRequest.getTimestamp())
                .idvIdValue(loadStateRequest.getIdvIdValue())
                .attempts(attempts)
                .build();
        final LockoutPolicy policy = policyLoader.load(loadStateRequest);
        return policy.calculateLockoutState(request);
    }

}
