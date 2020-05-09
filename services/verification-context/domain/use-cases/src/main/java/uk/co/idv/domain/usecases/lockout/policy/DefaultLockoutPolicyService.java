package uk.co.idv.domain.usecases.lockout.policy;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.policy.PolicyLevelConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;
import uk.co.idv.domain.usecases.policy.DefaultPolicyService;

@Slf4j
public class DefaultLockoutPolicyService extends DefaultPolicyService<LockoutPolicy> implements LockoutPolicyService {

    public DefaultLockoutPolicyService(final LockoutPolicyDao dao) {
        super(dao, new MultipleLockoutPoliciesHandler(), new PolicyLevelConverter());
    }

}
