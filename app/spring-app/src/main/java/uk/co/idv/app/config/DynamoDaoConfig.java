package uk.co.idv.app.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.domain.usecases.verificationcontext.policy.VerificationPolicyDao;
import uk.co.idv.dynamo.ttl.CurrentEpochSecondProvider;
import uk.co.idv.dynamo.ttl.EpochSecondProvider;
import uk.co.idv.repository.dynamo.DynamoClientFactory;
import uk.co.idv.repository.dynamo.VerificationContextDynamoConfig;
import uk.co.idv.repository.dynamo.onetimepasscode.OneTimePasscodeDynamoConfig;
import uk.co.idv.repository.inmemory.verificationcontext.InMemoryVerificationPolicyDao;
import uk.co.idv.utils.json.converter.jackson.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.JsonConverter;

@Configuration
@Profile("!stubbed")
public class DynamoDaoConfig {

    private final AmazonDynamoDB client = new DynamoClientFactory().build();
    private final VerificationContextDynamoConfig verificationContextConfig = new VerificationContextDynamoConfig(client);
    private final OneTimePasscodeDynamoConfig oneTimePasscodeConfig = new OneTimePasscodeDynamoConfig(client);

    @Bean
    public EpochSecondProvider epochSecondProvider() {
        return new CurrentEpochSecondProvider();
    }

    @Bean
    public IdentityDao identityDao(final JsonConverter jsonConverter) {
        return verificationContextConfig.identityDao(jsonConverter);
    }

    @Bean
    public VerificationContextDao verificationContextDao(final JsonConverter jsonConverter,
                                                         final EpochSecondProvider epochSecondProvider) {
        return verificationContextConfig.verificationContextDao(jsonConverter, epochSecondProvider);
    }

    @Bean
    public VerificationAttemptDao verificationAttemptsDao(final JsonConverter jsonConverter) {
        return verificationContextConfig.verificationAttemptDao(jsonConverter);
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter) {
        return verificationContextConfig.lockoutPolicyDao(jsonConverter);
    }

    @Bean
    public OneTimePasscodeVerificationDao oneTimePasscodeVerificationDao(final JsonConverter jsonConverter,
                                                                         final EpochSecondProvider epochSecondProvider) {
        return oneTimePasscodeConfig.verificationDao(jsonConverter, epochSecondProvider);
    }

    //TODO replace with dynamo implementation
    @Bean
    public VerificationPolicyDao verificationPolicyDao() {
        return new InMemoryVerificationPolicyDao();
    }

    @Bean
    public JsonConverter jsonConverter(@Qualifier("dynamoObjectMapper") final ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
