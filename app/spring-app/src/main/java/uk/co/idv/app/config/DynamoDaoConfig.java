package uk.co.idv.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptDao;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.DynamoClientFactory;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.json.JacksonJsonConverter;
import uk.co.idv.repository.dynamo.json.JsonConverter;

@Configuration
@Profile("!stub")
public class DynamoDaoConfig {

    private final DynamoConfig config = new DynamoConfig(new DynamoClientFactory().build());

    @Bean
    public IdentityDao identityDao() {
        return config.identityDao();
    }

    @Bean
    public VerificationContextDao verificationContextDao(final JsonConverter jsonConverter,
                                                         final TimeGenerator timeGenerator) {
        return config.verificationContextDao(jsonConverter, timeGenerator);
    }

    @Bean
    public VerificationAttemptDao verificationAttemptsDao(final JsonConverter jsonConverter) {
        return config.verificationAttemptsDao(jsonConverter);
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter) {
        return config.lockoutPolicyDao(jsonConverter);
    }

    @Bean
    public JsonConverter jsonConverter(@Qualifier("dynamoObjectMapper") final ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
