package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutFacade;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutService;
import uk.co.idv.domain.usecases.lockout.LockoutFacade;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.lockout.attempt.LockoutAttemptRecorder;
import uk.co.idv.domain.usecases.lockout.attempt.RecordAttemptRequestConverter;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptPersister;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptsLoader;
import uk.co.idv.domain.usecases.lockout.policy.DefaultLockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.policy.InitialLockoutPolicyCreator;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.state.LockoutRequestService;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateLoader;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateResetter;
import uk.co.idv.domain.usecases.lockout.state.LockoutStateValidator;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;

@Configuration
public class LockoutConfig {

    @Bean
    public RecordAttemptRequestConverter recordAttemptRequestConverter() {
        return new RecordAttemptRequestConverter();
    }

    @Bean
    public LockoutPolicyService lockoutPolicyService(final LockoutPolicyDao dao) {
        return new DefaultLockoutPolicyService(dao);
    }

    @Bean
    public LockoutStateRequestConverter lockoutStateRequestConverter() {
        return new LockoutStateRequestConverter();
    }

    @Bean
    public LockoutAttemptRecorder lockoutAttemptRecorder(final RecordAttemptRequestConverter requestConverter,
                                                         final LockoutStateLoader stateLoader,
                                                         final LockoutPolicyService policyService,
                                                         final LockoutStateResetter stateResetter,
                                                         final VerificationAttemptPersister statePersister) {
        return LockoutAttemptRecorder.builder()
                .requestConverter(requestConverter)
                .policyService(policyService)
                .stateLoader(stateLoader)
                .stateResetter(stateResetter)
                .attemptPersister(statePersister)
                .build();
    }

    @Bean
    public LockoutRequestService requestService(final VerificationAttemptsLoader attemptsLoader,
                                                final LockoutStateRequestConverter requestConverter) {
        return LockoutRequestService.builder()
                .attemptsLoader(attemptsLoader)
                .requestConverter(requestConverter)
                .build();
    }

    @Bean
    public LockoutStateResetter lockoutStateResetter(final LockoutRequestService requestService,
                                                     final LockoutPolicyService policyService,
                                                     final VerificationAttemptDao dao) {
        return LockoutStateResetter.builder()
                .requestService(requestService)
                .policyService(policyService)
                .dao(dao)
                .build();
    }

    @Bean
    public LockoutStateLoader lockoutStateLoader(final LockoutRequestService requestService,
                                                 final LockoutPolicyService policyService) {
        return LockoutStateLoader.builder()
                .requestService(requestService)
                .policyService(policyService)
                .build();
    }

    @Bean
    public LockoutStateValidator lockoutStateValidator(final LockoutStateLoader stateLoader) {
        return LockoutStateValidator.builder()
                .stateLoader(stateLoader)
                .build();
    }

    @Bean
    public LockoutService lockoutService(final LockoutAttemptRecorder attemptRecorder,
                                         final LockoutStateLoader stateLoader,
                                         final LockoutStateValidator stateValidator,
                                         final LockoutStateResetter stateResetter) {
        return DefaultLockoutService.builder()
                .attemptRecorder(attemptRecorder)
                .stateLoader(stateLoader)
                .stateValidator(stateValidator)
                .stateResetter(stateResetter)
                .build();
    }

    @Bean
    public LockoutFacade lockoutFacade(final IdentityService identityService,
                                       final LockoutService lockoutService) {
        return DefaultLockoutFacade.builder()
                .timeProvider(new CurrentTimeProvider())
                .identityService(identityService)
                .lockoutService(lockoutService)
                .build();
    }

    @Bean
    public InitialLockoutPolicyCreator populateLockoutPoliciesListener(final LockoutPolicyProvider policyProvider,
                                                                       final LockoutPolicyService policyService) {
        return new InitialLockoutPolicyCreator(policyProvider, policyService);
    }

    @Bean
    public CreateLockoutPoliciesListener createLockoutPoliciesListener(final InitialLockoutPolicyCreator policyCreator) {
        return new CreateLockoutPoliciesListener(policyCreator);
    }

}
