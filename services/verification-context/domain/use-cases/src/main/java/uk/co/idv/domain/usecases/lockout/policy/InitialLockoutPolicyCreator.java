package uk.co.idv.domain.usecases.lockout.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService.LockoutPoliciesAlreadyExistException;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class InitialLockoutPolicyCreator {

    private final LockoutPolicyProvider policyProvider;
    private final LockoutPolicyService policyService;

    public boolean create() {
        final Collection<LockoutPolicy> policies = policyProvider.getPolicies();
        boolean allCreated = true;
        for (final LockoutPolicy policy : policies) {
            if (!createPolicy(policy)) {
                allCreated = false;
            }
        }
        return allCreated;
    }

    private boolean createPolicy(final LockoutPolicy policy) {
        try {
            policyService.create(policy);
            return true;
        } catch (final LockoutPoliciesAlreadyExistException e) {
            log(e);
            return false;
        }
    }

    private void log(final LockoutPoliciesAlreadyExistException e) {
        log.debug(e.getMessage(), e);
        log.warn(e.getMessage());
    }

}
