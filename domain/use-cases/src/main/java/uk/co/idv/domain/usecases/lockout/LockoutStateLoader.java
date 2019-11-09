package uk.co.idv.domain.usecases.lockout;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.LockoutState;
import uk.co.idv.domain.entities.lockout.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.LockoutStateRequestConverter;
import uk.co.idv.domain.entities.lockout.VerificationAttempts;

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
