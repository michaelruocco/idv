package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributesConverter;
import uk.co.idv.api.lockout.policy.soft.SoftLockIntervalDtoConverter;
import uk.co.idv.api.lockout.policy.soft.SoftLockIntervalDtosConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.RandomIdGenerator;
import uk.co.idv.uk.api.channel.UkObjectMapperSingleton;
import uk.co.idv.uk.api.lockout.policy.UkLockoutPolicyAttributesConverter;
import uk.co.idv.uk.domain.entities.lockout.UkLockoutPolicyProvider;

public class UkConfig {

    public ObjectMapper jsonApiObjectMapper() {
        return UkObjectMapperSingleton.jsonApiInstance();
    }

    public ObjectMapper objectMapper() {
        return UkObjectMapperSingleton.instance();
    }

    public IdGenerator idGenerator() {
        return new RandomIdGenerator();
    }

    public LockoutPolicyProvider lockoutPolicyProvider() {
        return new UkLockoutPolicyProvider();
    }

    public DefaultLockoutPolicyAttributesConverter lockoutPolicyAttributesConverter() {
        return new UkLockoutPolicyAttributesConverter(
                recordAttemptStrategyFactory(),
                softLockIntervalDtosConverter()
        );
    }

    private RecordAttemptStrategyFactory recordAttemptStrategyFactory() {
        return new RecordAttemptStrategyFactory();
    }

    private SoftLockIntervalDtosConverter softLockIntervalDtosConverter() {
        return new SoftLockIntervalDtosConverter(softLockIntervalDtoConverter());
    }

    private SoftLockIntervalDtoConverter softLockIntervalDtoConverter() {
        return new SoftLockIntervalDtoConverter();
    }

}
