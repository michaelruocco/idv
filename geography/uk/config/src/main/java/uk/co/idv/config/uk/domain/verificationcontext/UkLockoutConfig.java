package uk.co.idv.config.uk.domain.verificationcontext;

import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutFacade;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutService;
import uk.co.idv.domain.usecases.lockout.LockoutFacade;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.lockout.attempt.DefaultVerificationAttemptsLoader;
import uk.co.idv.domain.usecases.lockout.attempt.LockoutAttemptRecorder;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptPersister;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptsLoader;
import uk.co.idv.domain.usecases.lockout.policy.DefaultLockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyCreator;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.state.LockoutRequestService;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateLoader;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateResetter;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateValidator;
import uk.co.idv.domain.usecases.policy.PolicyCreator;
import uk.co.idv.uk.domain.entities.policy.lockout.UkLockoutPolicyProvider;

public class UkLockoutConfig {

    public LockoutFacade lockoutFacade(final IdentityService identityService,
                                       final LockoutService lockoutService) {
        return DefaultLockoutFacade.builder()
                .identityService(identityService)
                .lockoutService(lockoutService)
                .build();
    }

    public LockoutService lockoutService(final VerificationAttemptDao attemptDao,
                                         final LockoutPolicyDao policyDao) {
        return DefaultLockoutService.builder()
                .attemptRecorder(attemptRecorder(attemptDao, policyDao))
                .stateLoader(stateLoader(attemptDao, policyDao))
                .stateValidator(stateValidator(attemptDao, policyDao))
                .stateResetter(stateResetter(attemptDao, policyDao))
                .build();
    }

    public PolicyCreator policyCreator(final LockoutPolicyDao dao) {
        return LockoutPolicyCreator.builder()
                .policyProvider(new UkLockoutPolicyProvider())
                .policyService(policyService(dao))
                .build();
    }

    public LockoutPolicyService policyService(final LockoutPolicyDao dao) {
        return new DefaultLockoutPolicyService(dao);
    }

    private LockoutAttemptRecorder attemptRecorder(final VerificationAttemptDao attemptDao,
                                                   final LockoutPolicyDao policyDao) {
        return LockoutAttemptRecorder.builder()
                .attemptPersister(attemptPersister(attemptDao))
                .stateLoader(stateLoader(attemptDao, policyDao))
                .stateResetter(stateResetter(attemptDao, policyDao))
                .policyService(policyService(policyDao))
                .build();
    }

    private LockoutStateValidator stateValidator(final VerificationAttemptDao attemptDao,
                                                 final LockoutPolicyDao policyDao) {
        return LockoutStateValidator.builder()
                .stateLoader(stateLoader(attemptDao, policyDao))
                .build();
    }

    private LockoutStateLoader stateLoader(final VerificationAttemptDao attemptDao,
                                           final LockoutPolicyDao policyDao) {
        return LockoutStateLoader.builder()
                .requestService(requestService(attemptDao))
                .policyService(policyService(policyDao))
                .build();
    }

    private LockoutStateResetter stateResetter(final VerificationAttemptDao attemptDao,
                                               final LockoutPolicyDao policyDao) {
        return LockoutStateResetter.builder()
                .requestService(requestService(attemptDao))
                .policyService(policyService(policyDao))
                .dao(attemptDao)
                .build();
    }

    private LockoutRequestService requestService(final VerificationAttemptDao dao) {
        return LockoutRequestService.builder()
                .attemptsLoader(attemptsLoader(dao))
                .build();
    }

    private VerificationAttemptPersister attemptPersister(final VerificationAttemptDao dao) {
        return VerificationAttemptPersister.builder()
                .attemptsLoader(attemptsLoader(dao))
                .dao(dao)
                .build();
    }

    private VerificationAttemptsLoader attemptsLoader(final VerificationAttemptDao dao) {
        return DefaultVerificationAttemptsLoader.builder()
                .dao(dao)
                .build();
    }

}
