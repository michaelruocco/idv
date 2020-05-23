package uk.co.idv.app.config.domain.verificationcontext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.config.uk.domain.UkLockoutConfig;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.lockout.LockoutFacade;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;

@Configuration
public class LockoutConfig {

    private final UkLockoutConfig config = new UkLockoutConfig();

    @Bean
    public LockoutFacade lockoutFacade(final IdentityService identityService,
                                       final LockoutService lockoutService) {
        return config.lockoutFacade(identityService, lockoutService);
    }

    @Bean
    public LockoutService lockoutService(final VerificationAttemptDao attemptDao,
                                         final LockoutPolicyDao policyDao) {
        return config.lockoutService(attemptDao, policyDao);
    }

    @Bean
    public LockoutPolicyService lockoutPolicyService(final LockoutPolicyDao policyDao) {
        return config.policyService(policyDao);
    }

    @Bean
    public CreateLockoutPoliciesListener createLockoutPoliciesListener(final LockoutPolicyDao dao) {
        return new CreateLockoutPoliciesListener(config.policyCreator(dao));
    }

}
