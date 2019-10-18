package uk.co.mruoc.idv.lockout.domain.service;

import lombok.Builder;
import uk.co.mruoc.idv.lockout.domain.model.LockoutState;
import uk.co.mruoc.idv.lockout.domain.model.VerificationAttempts;

@Builder
public class LockoutStateLoader {

    private final VerificationAttemptsLoader attemptsLoader;
    private final LockoutPolicyService policyService;
    private final LockoutStateRequestConverter requestConverter;

    public LockoutState load(final LockoutStateRequest request) {
        final VerificationAttempts attempts = attemptsLoader.load(request.getIdvIdValue());
        return calculateLockoutState(request, attempts);
    }

    private LockoutState calculateLockoutState(final LockoutStateRequest lockoutRequest,
                                               final VerificationAttempts attempts) {
        final CalculateLockoutStateRequest calculateRequest = requestConverter.toCalculateRequest(lockoutRequest, attempts);
        return policyService.calculateState(calculateRequest);
    }

}
