package uk.co.idv.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.uk.config.lockout.UkLockoutPolicyProvider;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.entities.lockout.state.LockoutStateRequestConverter;
import uk.co.idv.api.lockout.JsonApiLockoutStateModule;
import uk.co.idv.api.verificationcontext.JsonApiVerificationContextModule;
import uk.co.idv.domain.usecases.util.CurrentTimeGenerator;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.RandomIdGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.entities.identity.alias.AliasFactory;
import uk.co.idv.domain.usecases.identity.DefaultIdentityService;
import uk.co.idv.domain.usecases.identity.IdentityService;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.ChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptsDao;
import uk.co.idv.json.lockout.LockoutStateCalculatorFactory;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutFacade;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutPolicyService;
import uk.co.idv.domain.usecases.lockout.DefaultLockoutService;
import uk.co.idv.domain.usecases.lockout.DefaultVerificationAttemptsLoader;
import uk.co.idv.domain.usecases.lockout.LockoutAttemptRecorder;
import uk.co.idv.domain.usecases.lockout.LockoutFacade;
import uk.co.idv.json.lockout.LockoutPolicyParametersConverter;
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
import uk.co.idv.domain.usecases.verificationcontext.MaxDurationExpiryCalculator;
import uk.co.idv.domain.usecases.verificationcontext.SequenceLoader;
import uk.co.idv.domain.usecases.verificationcontext.StubbedSequenceLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextCreator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextLoader;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextResultRecorder;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextService;
import uk.co.mruoc.jsonapi.ApiModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class DomainConfig {

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new ApiModule());
        mapper.registerModule(new ChannelModule());
        mapper.registerModule(new ActivityModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new JsonApiVerificationContextModule());
        mapper.registerModule(new JsonApiLockoutStateModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public IdGenerator idGenerator() {
        return new RandomIdGenerator();
    }

    @Bean
    public TimeGenerator timeService() {
        return new CurrentTimeGenerator();
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
    public RecordAttemptStrategyFactory recordAttemptStrategyFactory() {
        return new RecordAttemptStrategyFactory();
    }

    @Bean
    public LockoutStateCalculatorFactory stateCalculatorFactory() {
        return new LockoutStateCalculatorFactory();
    }

    @Bean
    public LockoutPolicyParametersConverter lockoutPolicyParametersConverter(final RecordAttemptStrategyFactory recordAttemptStrategyFactory,
                                                                             final LockoutStateCalculatorFactory lockoutStateCalculatorFactory) {
        return LockoutPolicyParametersConverter.builder()
                .recordAttemptStrategyFactory(recordAttemptStrategyFactory)
                .lockoutStateCalculatorFactory(lockoutStateCalculatorFactory)
                .build();
    }

    @Bean
    public LockoutPolicyProvider lockoutPolicyParametersProvider(final IdGenerator idGenerator) {
        return new UkLockoutPolicyProvider(idGenerator);
    }

    @Bean
    public LockoutPolicyService lockoutPolicyService(final LockoutPolicyDao dao,
                                                     final LockoutPolicyProvider policiesProvider) {
        final LockoutPolicyService policyService = new DefaultLockoutPolicyService(dao);
        policyService.savePolicies(policiesProvider.getPolicies());
        return policyService;
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
    public IdentityService identityService(final IdGenerator idGenerator,
                                           final IdentityDao dao) {
        return DefaultIdentityService.builder()
                .idGenerator(idGenerator)
                .dao(dao)
                .build();
    }

    @Bean
    public VerificationAttemptsLoader verificationAttemptsLoader(final IdGenerator idGenerator,
                                                                 final VerificationAttemptsDao dao) {
        return DefaultVerificationAttemptsLoader.builder()
                .idGenerator(idGenerator)
                .dao(dao)
                .build();
    }

    @Bean
    public VerificationAttemptPersister verificationAttemptPersister(final VerificationAttemptsLoader attemptLoader,
                                                                     final VerificationAttemptsDao dao) {
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
                                                     final VerificationAttemptsDao dao) {
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
    public VerificationContextLoader verificationContextLoader(final TimeGenerator timeGenerator,
                                                               final LockoutService lockoutService,
                                                               final VerificationContextDao dao) {
        return DefaultVerificationContextLoader.builder()
                .timeGenerator(timeGenerator)
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
                                                                 final TimeGenerator timeGenerator,
                                                                 final IdentityService identityService,
                                                                 final SequenceLoader sequenceLoader,
                                                                 final ExpiryCalculator expiryCalculator,
                                                                 final LockoutService lockoutService,
                                                                 final VerificationContextDao dao) {
        return VerificationContextCreator.builder()
                .idGenerator(idGenerator)
                .timeGenerator(timeGenerator)
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
    public LockoutFacade lockoutFacade(final TimeGenerator timeGenerator,
                                       final IdentityService identityService,
                                       final LockoutService lockoutService) {
        return DefaultLockoutFacade.builder()
                .timeGenerator(timeGenerator)
                .identityService(identityService)
                .lockoutService(lockoutService)
                .build();
    }

}
