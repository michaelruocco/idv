package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributesConverter;
import uk.co.idv.api.lockout.policy.soft.SoftLockIntervalDtoConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.entities.lockout.policy.recordattempt.RecordAttemptStrategyFactory;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.RandomIdGenerator;
import uk.co.idv.repository.mongo.activity.ActivityDocumentConverterDelegator;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverterDelegator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverterDelegator;
import uk.co.idv.repository.mongo.lockout.policy.soft.SoftLockIntervalDocumentConverter;
import uk.co.idv.repository.mongo.lockout.policy.soft.SoftLockIntervalDocumentsConverter;
import uk.co.idv.uk.api.channel.UkObjectMapperSingleton;
import uk.co.idv.uk.api.lockout.policy.UkLockoutPolicyAttributesConverter;
import uk.co.idv.uk.domain.entities.lockout.UkLockoutPolicyProvider;
import uk.co.idv.uk.repository.mongo.activity.UkActivityDocumentConverterDelegator;
import uk.co.idv.uk.repository.mongo.channel.UkChannelDocumentConverterDelegator;
import uk.co.idv.uk.repository.mongo.lockout.UkLockoutPolicyDocumentConverterDelegator;

public class UkConfig {

    public ObjectMapper objectMapper() {
        return UkObjectMapperSingleton.get();
    }

    public IdGenerator idGenerator() {
        return new RandomIdGenerator();
    }

    public LockoutPolicyProvider lockoutPolicyProvider() {
        return new UkLockoutPolicyProvider();
    }

    public ChannelDocumentConverterDelegator channelDocumentConverterDelegator() {
        return new UkChannelDocumentConverterDelegator();
    }

    public ActivityDocumentConverterDelegator activityDocumentConverterDelegator() {
        return new UkActivityDocumentConverterDelegator();
    }

    public DefaultLockoutPolicyAttributesConverter lockoutPolicyAttributesConverter() {
        return new UkLockoutPolicyAttributesConverter(
                recordAttemptStrategyFactory(),
                softLockIntervalDtoConverter()
        );
    }

    public LockoutPolicyDocumentConverterDelegator lockoutPolicyDocumentConverterDelegator() {
        return new UkLockoutPolicyDocumentConverterDelegator(
                recordAttemptStrategyFactory(),
                softLockIntervalDocumentsConverter()
        );
    }

    private RecordAttemptStrategyFactory recordAttemptStrategyFactory() {
        return new RecordAttemptStrategyFactory();
    }

    private SoftLockIntervalDtoConverter softLockIntervalDtoConverter() {
        return new SoftLockIntervalDtoConverter();
    }

    private SoftLockIntervalDocumentsConverter softLockIntervalDocumentsConverter() {
        return new SoftLockIntervalDocumentsConverter(softLockIntervalDocumentConverter());
    }

    private SoftLockIntervalDocumentConverter softLockIntervalDocumentConverter() {
        return new SoftLockIntervalDocumentConverter();
    }

}
