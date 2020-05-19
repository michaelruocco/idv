package uk.co.idv.app.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.policy.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.attempt.VerificationAttemptDao;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.dynamo.ttl.CurrentEpochSecondProvider;
import uk.co.idv.dynamo.ttl.EpochSecondProvider;
import uk.co.idv.repository.dynamo.DynamoClientFactory;
import uk.co.idv.repository.dynamo.VerificationContextDynamoConfig;
import uk.co.idv.repository.dynamo.onetimepasscode.OneTimePasscodeDynamoConfig;
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

    //TODO move with redis config
    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter) {
        return verificationContextConfig.lockoutPolicyDao(jsonConverter);
    }

    @Bean
    public OneTimePasscodeVerificationDao oneTimePasscodeVerificationDao(final JsonConverter jsonConverter,
                                                                         final EpochSecondProvider epochSecondProvider) {
        return oneTimePasscodeConfig.verificationDao(jsonConverter, epochSecondProvider);
    }

}
