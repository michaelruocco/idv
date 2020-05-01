package uk.co.idv.domain.usecases.lockout.state;

import lombok.Builder;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.state.CalculateLockoutStateRequest;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutState;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequest;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;

@Builder
public class LockoutStateLoader {

    private final LockoutPolicyService policyService;
    private final LockoutRequestService requestService;

    public LockoutState load(final LockoutStateRequest request) {
        final LockoutPolicy policy = policyService.load(request);
        final CalculateLockoutStateRequest calculateRequest = requestService.toCalculateRequest(request);
        return policy.calculateState(calculateRequest);
    }

}
