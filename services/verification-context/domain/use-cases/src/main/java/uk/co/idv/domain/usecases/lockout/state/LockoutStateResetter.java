package uk.co.idv.domain.usecases.lockout.state;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptsLoader;

@Builder
@Slf4j
public class LockoutStateResetter {

    private final VerificationAttemptsLoader attemptsLoader;
    private final LockoutPolicyService policyService;
    private final LockoutStateRequestConverter requestConverter;
    private final VerificationAttemptDao dao;

    public void reset(final LockoutStateRequest lockoutRequest) {
        log.info("resetting lockout state for request {}", lockoutRequest);
        final LockoutPolicy policy = policyService.load(lockoutRequest);
        final CalculateLockoutStateRequest calculateRequest = toCalculateStateRequest(lockoutRequest);
        final VerificationAttempts resetAttempts = policy.reset(calculateRequest);
        dao.save(resetAttempts);
    }

    private CalculateLockoutStateRequest toCalculateStateRequest(final LockoutStateRequest stateRequest) {
        final VerificationAttempts attempts = attemptsLoader.load(stateRequest.getIdvIdValue());
        return requestConverter.toCalculateRequest(stateRequest, attempts);
    }

}
