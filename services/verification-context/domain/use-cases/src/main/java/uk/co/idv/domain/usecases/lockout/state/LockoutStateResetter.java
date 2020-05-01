package uk.co.idv.domain.usecases.lockout.state;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;

@Builder
@Slf4j
public class LockoutStateResetter {

    private final LockoutRequestService requestService;
    private final LockoutPolicyService policyService;
    private final VerificationAttemptDao dao;

    public void reset(final LockoutStateRequest lockoutRequest) {
        log.info("resetting lockout state for request {}", lockoutRequest);
        final LockoutPolicy policy = policyService.load(lockoutRequest);
        final CalculateLockoutStateRequest calculateRequest = requestService.toCalculateRequest(lockoutRequest);
        final VerificationAttempts resetAttempts = policy.reset(calculateRequest);
        dao.save(resetAttempts);
    }

}
