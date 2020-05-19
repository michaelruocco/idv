package uk.co.idv.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicyProvider;
import uk.co.idv.domain.entities.verificationcontext.policy.VerificationPolicyProvider;
import uk.co.idv.domain.usecases.util.id.IdGenerator;
import uk.co.idv.uk.config.UkConfig;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.idv.utils.json.converter.jackson.JacksonJsonConverter;

@Configuration
public class UkSpringConfig {

    private final UkConfig ukConfig = new UkConfig();

    @Primary
    @Bean
    public ObjectMapper jsonApiObjectMapper() {
        return ukConfig.jsonApiObjectMapper();
    }

    @Bean
    public ObjectMapper persistenceObjectMapper() {
        return ukConfig.objectMapper();
    }

    @Bean
    public JsonConverter jsonConverter(@Qualifier("persistenceObjectMapper") final ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
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
    public VerificationPolicyProvider verificationPolicyProvider() {
        return ukConfig.verificationPolicyProvider();
    }

}
