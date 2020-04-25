package uk.co.idv.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.entities.lockout.policy.state.LockoutStateRequestConverter;
import uk.co.idv.domain.usecases.identity.data.AccountLoader;
import uk.co.idv.domain.usecases.identity.data.AliasLoader;
import uk.co.idv.domain.usecases.identity.data.IdentityDataService;
import uk.co.idv.domain.usecases.identity.data.MobileDeviceLoader;
import uk.co.idv.domain.usecases.identity.data.PhoneNumberLoader;
import uk.co.idv.domain.usecases.lockout.InitialLockoutPolicyCreator;
import uk.co.idv.domain.usecases.util.time.CurrentTimeProvider;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.domain.usecases.util.time.TimeProvider;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.DefaultIdentityService;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptDao;
import uk.co.idv.api.lockout.state.LockoutStateCalculatorFactory;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutFacade;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutService;
import uk.co.idv.domain.usecases.lockout.DefaultVerificationAttemptsLoader;
import uk.co.idv.domain.usecases.lockout.LockoutAttemptRecorder;
import uk.co.idv.domain.usecases.lockout.LockoutFacade;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.LockoutService;
import uk.co.idv.domain.usecases.lockout.LockoutStateLoader;
import uk.co.idv.domain.usecases.lockout.LockoutStateResetter;
import uk.co.idv.domain.usecases.lockout.LockoutStateValidator;
import uk.co.idv.domain.usecases.lockout.RecordAttemptRequestConverter;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptPersister;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptsLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.usecases.verificationcontext.DefaultVerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.DefaultVerificationContextService;
import uk.co.idv.domain.usecases.verificationcontext.ExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.expiry.MaxDurationExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.SequenceLoader;
import uk.co.idv.domain.usecases.verificationcontext.sequences.StubbedSequenceLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextCreator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextResultRecorder;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextService;

@Configuration
public class VerificationContextDomainConfig {

    @Bean
    public TimeProvider timeService() {
        return new CurrentTimeProvider();
    }

    @Bean
    public ExpiryCalculator expiryCalculator() {
        return new MaxDurationExpiryCalculator();
    }

    @Bean
    public RecordAttemptRequestConverter recordAttemptRequestConverter() {
        return new RecordAttemptRequestConverter();
    }

    @Bean
    public SequenceLoader sequenceLoader() {
        return new StubbedSequenceLoader();
    }

    @Bean
    public LockoutStateCalculatorFactory stateCalculatorFactory() {
        return new LockoutStateCalculatorFactory();
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
    public AliasFactory aliasFactory() {
        return new AliasFactory();
    }

    @Bean
    public AliasLoader aliasLoader() {
        return new AliasLoader();
    }

    @Bean
    public PhoneNumberLoader phoneNumberLoader() {
        return new PhoneNumberLoader();
    }

    @Bean
    public AccountLoader accountLoader() {
        return new AccountLoader();
    }

    @Bean
    public MobileDeviceLoader mobileApplicationEligibleLoader(final TimeProvider timeProvider) {
        return new MobileDeviceLoader(timeProvider);
    }

    @Bean
    public IdentityDataService identityDataService(final AliasLoader aliasLoader,
                                                   final PhoneNumberLoader phoneNumberLoader,
                                                   final AccountLoader accountLoader,
                                                   final MobileDeviceLoader mobileDeviceLoader) {
        return IdentityDataService.builder()
                .aliasLoader(aliasLoader)
                .phoneNumberLoader(phoneNumberLoader)
                .accountLoader(accountLoader)
                .mobileDeviceLoader(mobileDeviceLoader)
                .build();
    }

    @Bean
    public IdentityService identityService(final IdGenerator idGenerator,
                                           final IdentityDataService dataService,
                                           final IdentityDao dao) {
        return DefaultIdentityService.builder()
                .idGenerator(idGenerator)
                .dataService(dataService)
                .dao(dao)
                .build();
    }

    @Bean
    public VerificationAttemptsLoader verificationAttemptsLoader(final IdGenerator idGenerator,
                                                                 final VerificationAttemptDao dao) {
        return DefaultVerificationAttemptsLoader.builder()
                .idGenerator(idGenerator)
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
    public LockoutStateResetter lockoutStateResetter(final VerificationAttemptsLoader attemptsLoader,
                                                     final LockoutPolicyService policyService,
                                                     final LockoutStateRequestConverter requestConverter,
                                                     final VerificationAttemptDao dao) {
        return LockoutStateResetter.builder()
                .attemptsLoader(attemptsLoader)
                .policyService(policyService)
                .requestConverter(requestConverter)
                .dao(dao)
                .build();
    }

    @Bean
    public LockoutStateLoader lockoutStateLoader(final VerificationAttemptsLoader attemptsLoader,
                                                 final LockoutPolicyService policyService,
                                                 final LockoutStateRequestConverter requestConverter) {
        return LockoutStateLoader.builder()
                .attemptsLoader(attemptsLoader)
                .policyService(policyService)
                .requestConverter(requestConverter)
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
    public VerificationContextLoader verificationContextLoader(final TimeProvider timeProvider,
                                                               final LockoutService lockoutService,
                                                               final VerificationContextDao dao) {
        return DefaultVerificationContextLoader.builder()
                .timeProvider(timeProvider)
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
    public VerificationContextCreator verificationContextCreator(final IdGenerator idGenerator,
                                                                 final TimeProvider timeProvider,
                                                                 final IdentityService identityService,
                                                                 final SequenceLoader sequenceLoader,
                                                                 final ExpiryCalculator expiryCalculator,
                                                                 final LockoutService lockoutService,
                                                                 final VerificationContextDao dao) {
        return VerificationContextCreator.builder()
                .idGenerator(idGenerator)
                .timeProvider(timeProvider)
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
    public LockoutFacade lockoutFacade(final TimeProvider timeProvider,
                                       final IdentityService identityService,
                                       final LockoutService lockoutService) {
        return DefaultLockoutFacade.builder()
                .timeProvider(timeProvider)
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
