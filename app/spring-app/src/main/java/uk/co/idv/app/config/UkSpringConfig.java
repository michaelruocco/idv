package uk.co.idv.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributesConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.util.IdGenerator;
import uk.co.idv.repository.mongo.activity.ActivityDocumentConverterDelegator;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverterDelegator;
import uk.co.idv.repository.mongo.lockout.policy.LockoutPolicyDocumentConverterDelegator;
import uk.co.idv.uk.config.UkConfig;

@Configuration
public class UkSpringConfig {

    private final UkConfig ukConfig = new UkConfig();

    @Bean
    public ObjectMapper objectMapper() {
        return ukConfig.objectMapper();
    }

    @Bean
    public IdGenerator idGenerator() {
        return ukConfig.idGenerator();
    }

    @Bean
    public LockoutPolicyProvider lockoutPolicyProvider() {
        return ukConfig.lockoutPolicyProvider();
    }

    @Bean
    public ChannelDocumentConverterDelegator channelDocumentConverterDelegator() {
        return ukConfig.channelDocumentConverterDelegator();
    }

    @Bean
    public ActivityDocumentConverterDelegator activityDocumentConverterDelegator() {
        return ukConfig.activityDocumentConverterDelegator();
    }

    @Bean
    public LockoutPolicyDocumentConverterDelegator lockoutPolicyDocumentConverterDelegator() {
        return ukConfig.lockoutPolicyDocumentConverterDelegator();
    }

    @Bean
    public DefaultLockoutPolicyAttributesConverter lockoutPolicyAttributesConverter() {
        return ukConfig.lockoutPolicyAttributesConverter();
    }

}
