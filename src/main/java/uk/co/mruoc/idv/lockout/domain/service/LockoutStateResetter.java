package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.UUID;

@Builder
@Slf4j
public class LockoutStateResetter {

    private final VerificationAttemptsLoader attemptsLoader;
    private final VerificationAttemptsDao dao;
    private final LockoutPolicyLoader policyLoader;

    public LockoutState reset(final LoadLockoutStateRequest loadRequest) {
        log.info("resetting lockout state for request {}", loadRequest);
        final VerificationAttempts attempts = loadAttempts(loadRequest.getIdvIdValue());
        final LockoutPolicy policy = policyLoader.load(loadRequest);
        final VerificationAttempts resetAttempts = reset(attempts, policy, loadRequest);
        dao.save(resetAttempts);
        return calculateState(resetAttempts, policy, loadRequest);
    }

    private VerificationAttempts reset(final VerificationAttempts attempts,
                                       final LockoutPolicy policy,
                                       final LoadLockoutStateRequest loadRequest) {
        final ResetAttemptsRequest resetRequest = ResetAttemptsRequest.builder()
                .channelId(loadRequest.getChannelId())
                .activityName(loadRequest.getActivityName())
                .alias(loadRequest.getAlias())
                .attempts(attempts)
                .build();
        return policy.reset(resetRequest);
    }

    private LockoutState calculateState(final VerificationAttempts attempts,
                                        final LockoutPolicy policy,
                                        final LoadLockoutStateRequest loadRequest) {
        final CalculateLockoutStateRequest calculateRequest = CalculateLockoutStateRequest.builder()
                .channelId(loadRequest.getChannelId())
                .activityName(loadRequest.getActivityName())
                .alias(loadRequest.getAlias())
                .idvIdValue(loadRequest.getIdvIdValue())
                .timestamp(loadRequest.getTimestamp())
                .attempts(attempts)
                .build();
        return policy.calculateLockoutState(calculateRequest);
    }

    private VerificationAttempts loadAttempts(final UUID idvId) {
        return attemptsLoader.load(idvId);
    }

}
