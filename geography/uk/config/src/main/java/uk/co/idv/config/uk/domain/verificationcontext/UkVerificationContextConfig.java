package uk.co.idv.config.uk.domain.verificationcontext;

import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.policy.PolicyCreator;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.verificationcontext.DefaultVerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.DefaultVerificationContextService;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextCreator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextService;
import uk.co.idv.domain.usecases.verificationcontext.expiry.MaxDurationExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.policy.DefaultVerificationPolicyService;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyCreator;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyService;
import uk.co.idv.domain.usecases.verificationcontext.result.DefaultVerificationContextResultRecorder;
import uk.co.idv.domain.usecases.verificationcontext.result.VerificationContextResultRecorder;
import uk.co.idv.domain.usecases.verificationcontext.sequence.DefaultSequenceLoader;
import uk.co.idv.domain.usecases.verificationcontext.sequence.SequenceLoader;
import uk.co.idv.uk.domain.entities.policy.sequence.UkVerificationPolicyProvider;

public class UkVerificationContextConfig {

    //TODO consider pulling out a facade to remove dependency on identity and lockout service in context service
    public VerificationContextService verificationContextService(final IdentityService identityService,
                                                                 final LockoutService lockoutService,
                                                                 final VerificationPolicyDao policyDao,
                                                                 final VerificationContextDao contextDao) {
        return DefaultVerificationContextService.builder()
                .creator(contextCreator(identityService, lockoutService, policyDao, contextDao))
                .loader(contextLoader(lockoutService, contextDao))
                .resultRecorder(resultRecorder(lockoutService, contextDao))
                .build();
    }

    public PolicyCreator policyCreator(final VerificationPolicyDao dao) {
        return VerificationPolicyCreator.builder()
                .policyProvider(new UkVerificationPolicyProvider())
                .policyService(policyService(dao))
                .build();
    }

    public VerificationPolicyService policyService(final VerificationPolicyDao dao) {
        return new DefaultVerificationPolicyService(dao);
    }

    private VerificationContextCreator contextCreator(final IdentityService identityService,
                                                      final LockoutService lockoutService,
                                                      final VerificationPolicyDao policyDao,
                                                      final VerificationContextDao contextDao) {
        return VerificationContextCreator.builder()
                .idGenerator(new RandomIdGenerator())
                .timeProvider(new CurrentTimeProvider())
                .identityService(identityService)
                .sequenceLoader(sequenceLoader(policyDao))
                .expiryCalculator(new MaxDurationExpiryCalculator())
                .lockoutService(lockoutService)
                .dao(contextDao)
                .build();
    }

    private SequenceLoader sequenceLoader(final VerificationPolicyDao dao) {
        return new DefaultSequenceLoader(policyService(dao));
    }

    private VerificationContextLoader contextLoader(final LockoutService lockoutService,
                                                    final VerificationContextDao dao) {
        return DefaultVerificationContextLoader.builder()
                .timeProvider(new CurrentTimeProvider())
                .lockoutService(lockoutService)
                .dao(dao)
                .build();
    }

    private VerificationContextResultRecorder resultRecorder(final LockoutService lockoutService,
                                                                    final VerificationContextDao dao) {
        return DefaultVerificationContextResultRecorder.builder()
                .contextLoader(contextLoader(lockoutService, dao))
                .lockoutService(lockoutService)
                .dao(dao)
                .build();
    }

}
