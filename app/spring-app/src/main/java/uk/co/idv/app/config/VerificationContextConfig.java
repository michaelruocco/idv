package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyProvider;
import uk.co.idv.domain.usecases.util.id.RandomIdGenerator;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.domain.usecases.lockout.attempt.DefaultVerificationAttemptsLoader;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.lockout.attempt.RecordAttemptRequestConverter;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptPersister;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptsLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.usecases.verificationcontext.DefaultVerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.DefaultVerificationContextService;
import uk.co.idv.domain.usecases.verificationcontext.expiry.ExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.expiry.MaxDurationExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.policy.DefaultVerificationPolicyService;
import uk.co.idv.domain.usecases.verificationcontext.policy.InitialVerificationPolicyCreator;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyService;
import uk.co.idv.domain.usecases.verificationcontext.sequence.DefaultSequenceLoader;
import uk.co.idv.domain.usecases.verificationcontext.sequence.SequenceLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextCreator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.result.VerificationContextResultRecorder;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextService;

@Configuration
public class VerificationContextConfig {

    @Bean
    public ExpiryCalculator expiryCalculator() {
        return new MaxDurationExpiryCalculator();
    }

    @Bean
    public SequenceLoader sequenceLoader(final VerificationPolicyService policyService) {
        return new DefaultSequenceLoader(policyService);
    }

    @Bean
    public VerificationPolicyService verificationPolicyService(final VerificationPolicyDao dao) {
        return new DefaultVerificationPolicyService(dao);
    }

    @Bean
    public VerificationAttemptsLoader verificationAttemptsLoader(final VerificationAttemptDao dao) {
        return DefaultVerificationAttemptsLoader.builder()
                .idGenerator(new RandomIdGenerator())
                .dao(dao)
                .build();
    }

    @Bean
    public VerificationAttemptPersister verificationAttemptPersister(final VerificationAttemptsLoader attemptLoader,
                                                                     final VerificationAttemptDao dao) {
        return VerificationAttemptPersister.builder()
                .attemptsLoader(attemptLoader)
                .dao(dao)
                .build();
    }

    @Bean
    public VerificationContextLoader verificationContextLoader(final LockoutService lockoutService,
                                                               final VerificationContextDao dao) {
        return DefaultVerificationContextLoader.builder()
                .timeProvider(new CurrentTimeProvider())
                .lockoutService(lockoutService)
                .dao(dao)
                .build();
    }

    @Bean
    public VerificationContextResultRecorder verificationContextResultRecorder(final VerificationContextLoader contextLoader,
                                                                               final LockoutService lockoutService,
                                                                               final VerificationContextDao dao) {
        return VerificationContextResultRecorder.builder()
                .contextLoader(contextLoader)
                .lockoutService(lockoutService)
                .dao(dao)
                .build();
    }

    @Bean
    public VerificationContextCreator verificationContextCreator(final IdentityService identityService,
                                                                 final SequenceLoader sequenceLoader,
                                                                 final ExpiryCalculator expiryCalculator,
                                                                 final LockoutService lockoutService,
                                                                 final VerificationContextDao dao) {
        return VerificationContextCreator.builder()
                .idGenerator(new RandomIdGenerator())
                .timeProvider(new CurrentTimeProvider())
                .identityService(identityService)
                .sequenceLoader(sequenceLoader)
                .expiryCalculator(expiryCalculator)
                .lockoutService(lockoutService)
                .dao(dao)
                .build();

    }

    @Bean
    public VerificationContextService verificationContextService(final VerificationContextCreator creator,
                                                                 final VerificationContextLoader loader,
                                                                 final VerificationContextResultRecorder resultRecorder) {
        return DefaultVerificationContextService.builder()
                .creator(creator)
                .loader(loader)
                .resultRecorder(resultRecorder)
                .build();
    }

    @Bean
    public InitialVerificationPolicyCreator populateVerificationPoliciesListener(final VerificationPolicyProvider policyProvider,
                                                                                 final VerificationPolicyService policyService) {
        return new InitialVerificationPolicyCreator(policyProvider, policyService);
    }

    @Bean
    public CreateVerificationPoliciesListener createVerificationPoliciesListener(final InitialVerificationPolicyCreator policyCreator) {
        return new CreateVerificationPoliciesListener(policyCreator);
    }

}
