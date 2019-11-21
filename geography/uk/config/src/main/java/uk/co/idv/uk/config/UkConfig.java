package uk.co.idv.uk.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.domain.usecases.util.RandomIdGenerator;
import uk.co.idv.repository.mongo.activity.ActivityDocumentConverterDelegator;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverterDelegator;
import uk.co.idv.uk.api.channel.UkObjectMapperSingleton;
import uk.co.idv.uk.domain.entities.lockout.UkLockoutPolicyProvider;
import uk.co.idv.uk.repository.mongo.activity.UkActivityDocumentConverterDelegator;
import uk.co.idv.uk.repository.mongo.channel.UkChannelDocumentConverterDelegator;

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

}
