package uk.co.idv.app.config.domain.verificationcontext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.config.uk.domain.UkVerificationContextConfig;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextService;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyService;

@Configuration
public class VerificationContextConfig {

    private final UkVerificationContextConfig config = new UkVerificationContextConfig();

    @Bean
    public VerificationContextService verificationContextService(final IdentityService identityService,
                                                                 final LockoutService lockoutService,
                                                                 final VerificationPolicyDao policyDao,
                                                                 final VerificationContextDao contextDao) {
        return config.verificationContextService(identityService, lockoutService, policyDao, contextDao);
    }

    @Bean
    public VerificationPolicyService verificationPolicyService(final VerificationPolicyDao dao) {
        return config.policyService(dao);
    }

    @Bean
    public CreateVerificationPoliciesListener createVerificationPoliciesListener(final VerificationPolicyDao dao) {
        return new CreateVerificationPoliciesListener(config.policyCreator(dao));
    }

}
