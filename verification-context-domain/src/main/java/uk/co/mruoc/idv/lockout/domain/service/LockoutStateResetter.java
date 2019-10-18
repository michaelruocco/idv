package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

import java.util.UUID;

@Builder
@Slf4j
public class LockoutStateResetter {

    private final VerificationAttemptsLoader attemptsLoader;
    private final LockoutPolicyService policyService;
    private final LockoutStateRequestConverter requestConverter;
    private final VerificationAttemptsDao dao;

    public void reset(final LockoutStateRequest lockoutRequest) {
        log.info("resetting lockout state for request {}", lockoutRequest);
        final VerificationAttempts attempts = loadAttempts(lockoutRequest.getIdvIdValue());
        final CalculateLockoutStateRequest resetRequest = requestConverter.toCalculateRequest(lockoutRequest, attempts);
        final VerificationAttempts resetAttempts = policyService.resetAttempts(resetRequest);
        dao.save(resetAttempts);
    }

    private VerificationAttempts loadAttempts(final UUID idvId) {
        return attemptsLoader.load(idvId);
    }

}