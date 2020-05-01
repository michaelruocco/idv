package uk.co.idv.domain.usecases.lockout.state;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptsLoader;

@Builder
public class LockoutStateLoader {

    private final VerificationAttemptsLoader attemptsLoader;
    private final LockoutPolicyService policyService;
    private final LockoutStateRequestConverter requestConverter;

    public LockoutState load(final LockoutStateRequest request) {
        final LockoutPolicy policy = policyService.load(request);
        final VerificationAttempts attempts = attemptsLoader.load(request.getIdvIdValue());
        final CalculateLockoutStateRequest calculateRequest = requestConverter.toCalculateRequest(request, attempts);
        return policy.calculateState(calculateRequest);
    }

}
