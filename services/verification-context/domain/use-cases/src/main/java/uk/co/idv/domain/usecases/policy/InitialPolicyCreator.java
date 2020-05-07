package uk.co.idv.domain.usecases.policy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.Policy;
import uk.co.idv.domain.entities.policy.PolicyProvider;
import uk.co.idv.domain.usecases.policy.PolicyService.PoliciesAlreadyExistException;

import java.util.Collection;

@RequiredArgsConstructor
@Slf4j
public class InitialPolicyCreator<T extends Policy> {

    private final PolicyProvider<T> policyProvider;
    private final PolicyService<T> policyService;

    public boolean create() {
        final Collection<T> policies = policyProvider.getPolicies();
        boolean allCreated = true;
        for (final T policy : policies) {
            if (!createPolicy(policy)) {
                allCreated = false;
            }
        }
        return allCreated;
    }

    private boolean createPolicy(final T policy) {
        try {
            policyService.create(policy);
            return true;
        } catch (final PoliciesAlreadyExistException e) {
            log(e);
            return false;
        }
    }

    private void log(final PoliciesAlreadyExistException e) {
        log.debug(e.getMessage(), e);
        log.warn(e.getMessage());
    }

}
