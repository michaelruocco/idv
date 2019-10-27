package uk.co.mruoc.idv.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.mruoc.idv.api.IdvModule;
import uk.co.mruoc.idv.domain.service.CurrentTimeService;
import uk.co.mruoc.idv.domain.service.IdGenerator;
import uk.co.mruoc.idv.domain.service.RandomIdGenerator;
import uk.co.mruoc.idv.domain.service.TimeService;
import uk.co.mruoc.idv.identity.api.IdentityModule;
import uk.co.mruoc.idv.identity.dao.IdentityDao;
import uk.co.mruoc.idv.identity.domain.model.AliasFactory;
import uk.co.mruoc.idv.identity.domain.service.DefaultIdentityService;
import uk.co.mruoc.idv.identity.domain.service.IdentityService;
import uk.co.mruoc.idv.lockout.dao.InMemoryLockoutPolicyDao;
import uk.co.mruoc.idv.lockout.dao.LockoutPolicyDao;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.RsaMaxAttemptsLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutFacade;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutPolicyService;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutService;
import uk.co.mruoc.idv.lockout.domain.service.DefaultVerificationAttemptsLoader;
import uk.co.mruoc.idv.lockout.domain.service.LockoutAttemptRecorder;
import uk.co.mruoc.idv.lockout.domain.service.LockoutFacade;
import uk.co.mruoc.idv.lockout.domain.service.LockoutPolicyService;
import uk.co.mruoc.idv.lockout.domain.service.LockoutService;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateLoader;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateRequestConverter;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateResetter;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateValidator;
import uk.co.mruoc.idv.lockout.domain.service.RecordAttemptRequestConverter;
import uk.co.mruoc.idv.lockout.domain.service.VerificationAttemptPersister;
import uk.co.mruoc.idv.lockout.domain.service.VerificationAttemptsLoader;
import uk.co.mruoc.idv.lockout.jsonapi.JsonApiLockoutStateModule;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.service.DefaultVerificationContextLoader;
import uk.co.mruoc.idv.verificationcontext.domain.service.DefaultVerificationContextService;
import uk.co.mruoc.idv.verificationcontext.domain.service.ExpiryCalculator;
import uk.co.mruoc.idv.verificationcontext.domain.service.MaxDurationExpiryCalculator;
import uk.co.mruoc.idv.verificationcontext.domain.service.SequenceLoader;
import uk.co.mruoc.idv.verificationcontext.domain.service.StubbedSequenceLoader;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextCreator;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextResultRecorder;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextService;
import uk.co.mruoc.idv.verificationcontext.jsonapi.JsonApiVerificationContextModule;
import uk.co.mruoc.jsonapi.JsonApiModule;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class DomainConfig {

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdvModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new JsonApiModule());
        mapper.registerModule(new JsonApiVerificationContextModule());
        mapper.registerModule(new JsonApiLockoutStateModule());
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public IdGenerator idGenerator() {
        return new RandomIdGenerator();
    }

    @Bean
    public TimeService timeService() {
        return new CurrentTimeService();
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

    @Bean // TODO create mongo bean and move this bean into in memory dao config
    public LockoutPolicyDao lockoutPolicyDao() {
        return new InMemoryLockoutPolicyDao();
    }

    @Bean
    public LockoutPolicyService lockoutPolicyService(final LockoutPolicyDao dao) {
        final LockoutPolicyService policyService = new DefaultLockoutPolicyService(dao);
        final LockoutPolicyParameters parameters = new RsaMaxAttemptsLockoutPolicyParameters();
        policyService.addPolicy(parameters.toPolicy());
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
    public VerificationContextLoader verificationContextLoader(final TimeService timeService,
                                                               final LockoutService lockoutService,
                                                               final VerificationContextDao dao) {
        return DefaultVerificationContextLoader.builder()
                .timeService(timeService)
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
                                                                 final TimeService timeService,
                                                                 final IdentityService identityService,
                                                                 final SequenceLoader sequenceLoader,
                                                                 final ExpiryCalculator expiryCalculator,
                                                                 final LockoutService lockoutService,
                                                                 final VerificationContextDao dao) {
        return VerificationContextCreator.builder()
                .idGenerator(idGenerator)
                .timeService(timeService)
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
    public LockoutFacade lockoutFacade(final TimeService timeService,
                                       final IdentityService identityService,
                                       final LockoutService lockoutService) {
        return DefaultLockoutFacade.builder()
                .timeService(timeService)
                .identityService(identityService)
                .lockoutService(lockoutService)
                .build();
    }

}
