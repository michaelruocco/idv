package uk.co.idv.app.config.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.repository.redis.VerificationContextRedisConfig;
import uk.co.idv.utils.json.converter.JsonConverter;

@Configuration
@Profile("!stubbed")
public class RedisDaoConfig {

    private final VerificationContextRedisConfig verificationContextConfig = new VerificationContextRedisConfig();

    @Bean
    public VerificationPolicyDao verificationPolicyDao(final JsonConverter jsonConverter) {
        return verificationContextConfig.verificationPolicyDao(jsonConverter);
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter) {
        return verificationContextConfig.lockoutPolicyDao(jsonConverter);
    }

}
