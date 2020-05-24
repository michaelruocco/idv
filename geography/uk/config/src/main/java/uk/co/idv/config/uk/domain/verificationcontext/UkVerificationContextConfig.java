package uk.co.idv.config.uk.domain.verificationcontext;

import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.policy.PolicyCreator;
import uk.co.idv.domain.usecases.verificationcontext.DefaultIdentityUpserter;
import uk.co.idv.domain.usecases.verificationcontext.DefaultVerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.DefaultVerificationContextService;
import uk.co.idv.domain.usecases.verificationcontext.IdentityUpserter;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextCreator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextService;
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

    public VerificationContextService verificationContextService(final IdentityService identityService,
                                                                 final LockoutService lockoutService,
                                                                 final VerificationPolicyDao policyDao,
                                                                 final VerificationContextDao contextDao) {
        final IdentityUpserter identityUpserter = identityUpserter(identityService, lockoutService);
        return DefaultVerificationContextService.builder()
                .creator(contextCreator(identityUpserter, policyDao, contextDao))
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

    private VerificationContextCreator contextCreator(final IdentityUpserter identityUpserter,
                                                      final VerificationPolicyDao policyDao,
                                                      final VerificationContextDao contextDao) {
        return VerificationContextCreator.builder()
                .identityUpserter(identityUpserter)
                .sequenceLoader(sequenceLoader(policyDao))
                .dao(contextDao)
                .build();
    }

    private IdentityUpserter identityUpserter(final IdentityService identityService,
                                              final LockoutService lockoutService) {
        return DefaultIdentityUpserter.builder()
                .identityService(identityService)
                .lockoutService(lockoutService)
                .build();
    }

    private SequenceLoader sequenceLoader(final VerificationPolicyDao dao) {
        return new DefaultSequenceLoader(policyService(dao));
    }

    private VerificationContextLoader contextLoader(final LockoutService lockoutService,
                                                    final VerificationContextDao dao) {
        return DefaultVerificationContextLoader.builder()
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
