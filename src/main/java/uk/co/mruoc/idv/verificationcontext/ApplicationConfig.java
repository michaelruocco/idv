package uk.co.mruoc.idv.verificationcontext;

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
import uk.co.mruoc.idv.identity.dao.InMemoryIdentityDao;
import uk.co.mruoc.idv.identity.domain.service.DefaultIdentityService;
import uk.co.mruoc.idv.identity.domain.service.IdentityService;
import uk.co.mruoc.idv.lockout.dao.InMemoryVerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.dao.VerificationAttemptsDao;
import uk.co.mruoc.idv.lockout.domain.service.DefaultLockoutService;
import uk.co.mruoc.idv.lockout.domain.service.LockoutAttemptRecorder;
import uk.co.mruoc.idv.lockout.domain.service.LockoutService;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateCalculator;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateCalculatorMaxAttemptsThree;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateLoader;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateValidator;
import uk.co.mruoc.idv.lockout.domain.service.VerificationAttemptsLoader;
import uk.co.mruoc.idv.lockout.domain.service.VerificationResultConverter;
import uk.co.mruoc.idv.verificationcontext.domain.service.DefaultVerificationContextLoader;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextCreator;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextLoader;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextResultRecorder;
import uk.co.mruoc.idv.verificationcontext.jsonapi.JsonApiVerificationContextModule;
import uk.co.mruoc.idv.verificationcontext.dao.InMemoryVerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.dao.VerificationContextDao;
import uk.co.mruoc.idv.verificationcontext.domain.service.DefaultVerificationContextService;
import uk.co.mruoc.idv.verificationcontext.domain.service.ExpiryCalculator;
import uk.co.mruoc.idv.verificationcontext.domain.service.MaxDurationExpiryCalculator;
import uk.co.mruoc.idv.verificationcontext.domain.service.SequenceLoader;
import uk.co.mruoc.idv.verificationcontext.domain.service.StubbedSequenceLoader;
import uk.co.mruoc.idv.verificationcontext.domain.service.VerificationContextService;
import uk.co.mruoc.jsonapi.JsonApiModule;


import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

@Configuration
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonApiModule());
        mapper.registerModule(new IdvModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new JsonApiVerificationContextModule());
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
    public VerificationResultConverter resultConverter() {
        return new VerificationResultConverter();
    }

    @Bean
    public SequenceLoader sequenceLoader() {
        return new StubbedSequenceLoader();
    }

    @Bean
    public LockoutStateCalculator stateCalculator() {
        return new LockoutStateCalculatorMaxAttemptsThree();
    }

    @Bean
    public VerificationContextDao verificationContextDao() {
        return new InMemoryVerificationContextDao();
    }

    @Bean
    public VerificationAttemptsDao verificationAttemptsDao() {
        return new InMemoryVerificationAttemptsDao();
    }

    @Bean
    public IdentityDao identityDao() {
        return new InMemoryIdentityDao();
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
    public VerificationAttemptsLoader verificationAttemptsLoader(final VerificationAttemptsDao dao) {
        return VerificationAttemptsLoader.builder()
                .dao(dao)
                .build();
    }

    @Bean
    public LockoutAttemptRecorder lockoutAttemptRecorder(final VerificationResultConverter resultConverter,
                                                         final LockoutStateCalculator stateCalculator,
                                                         final VerificationAttemptsLoader attemptsLoader,
                                                         final VerificationAttemptsDao dao) {
        return LockoutAttemptRecorder.builder()
                .resultConverter(resultConverter)
                .stateCalculator(stateCalculator)
                .attemptsLoader(attemptsLoader)
                .dao(dao)
                .build();
    }

    @Bean
    public LockoutStateLoader lockoutStateLoader(final VerificationAttemptsLoader attemptsLoader,
                                                 final LockoutStateCalculator stateCalculator) {
        return LockoutStateLoader.builder()
                .stateCalculator(stateCalculator)
                .attemptsLoader(attemptsLoader)
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
                                         final LockoutStateValidator stateValidator) {
        return DefaultLockoutService.builder()
                .attemptRecorder(attemptRecorder)
                .stateLoader(stateLoader)
                .stateValidator(stateValidator)
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

}
