package uk.co.idv.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.co.idv.api.lockout.policy.DefaultLockoutPolicyAttributesConverter;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.uk.config.UkConfig;

@Configuration
public class UkSpringConfig {

    private final UkConfig ukConfig = new UkConfig();

    @Primary
    @Bean
    public ObjectMapper jsonApiObjectMapper() {
        return ukConfig.jsonApiObjectMapper();
    }

    @Bean
    public ObjectMapper dynamoObjectMapper() {
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
    public DefaultLockoutPolicyAttributesConverter lockoutPolicyAttributesConverter() {
        return ukConfig.lockoutPolicyAttributesConverter();
    }

}
