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
import uk.co.mruoc.idv.lockout.domain.service.LockoutService;
import uk.co.mruoc.idv.lockout.domain.service.LockoutStateCalculator;
import uk.co.mruoc.idv.lockout.domain.service.MaxAttemptsThreeLockoutStateCalculator;
import uk.co.mruoc.idv.lockout.domain.service.VerificationResultConverter;
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
        return new MaxAttemptsThreeLockoutStateCalculator();
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
    public LockoutService lockoutService(final VerificationResultConverter resultConverter,
                                         final VerificationAttemptsDao dao,
                                         final LockoutStateCalculator stateCalculator) {
        return DefaultLockoutService.builder()
                .resultConverter(resultConverter)
                .dao(dao)
                .stateCalculator(stateCalculator)
                .build();
    }

    @Bean
    public VerificationContextService verificationContextService(final IdGenerator idGenerator,
                                                                 final TimeService timeService,
                                                                 final IdentityService identityService,
                                                                 final SequenceLoader sequenceLoader,
                                                                 final ExpiryCalculator expiryCalculator,
                                                                 final VerificationContextDao dao,
                                                                 final LockoutService lockoutService) {
        return DefaultVerificationContextService.builder()
                .idGenerator(idGenerator)
                .timeService(timeService)
                .identityService(identityService)
                .sequenceLoader(sequenceLoader)
                .expiryCalculator(expiryCalculator)
                .dao(dao)
                .lockoutService(lockoutService)
                .build();
    }

}
