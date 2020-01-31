package uk.co.idv.app.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import uk.co.idv.domain.usecases.identity.IdentityDao;
import uk.co.idv.domain.usecases.lockout.LockoutPolicyDao;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptDao;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.CustomCredentialsProviderChain;
import uk.co.idv.repository.dynamo.DynamoClientFactory;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.json.JacksonJsonConverter;
import uk.co.idv.repository.dynamo.json.JsonConverter;

@Configuration
@Profile("!stub")
public class DynamoDaoConfig {

    @Bean
    @Profile("aws")
    @Scope("singleton")
    public AmazonDynamoDB awsClient() {
        return new DynamoClientFactory().standard()
                .build();
    }

    @Bean
    @Profile("!aws")
    @Scope("singleton")
    public AmazonDynamoDB client() {
        return new DynamoClientFactory()
                .withCredentialsProvider(new CustomCredentialsProviderChain())
                .build();
    }

    @Bean
    @Scope("singleton")
    public DynamoConfig config(final AmazonDynamoDB client) {
        return new DynamoConfig(client);
    }

    @Bean
    public IdentityDao identityDao(final DynamoConfig config) {
        return config.identityDao();
    }

    @Bean
    public VerificationContextDao verificationContextDao(final DynamoConfig config,
                                                         final JsonConverter jsonConverter,
                                                         final TimeGenerator timeGenerator) {
        return config.verificationContextDao(jsonConverter, timeGenerator);
    }

    @Bean
    public VerificationAttemptDao verificationAttemptsDao(final DynamoConfig config,
                                                          final JsonConverter jsonConverter) {
        return config.verificationAttemptDao(jsonConverter);
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final DynamoConfig config,
                                             final JsonConverter jsonConverter) {
        return config.lockoutPolicyDao(jsonConverter);
    }

    @Bean
    public JsonConverter jsonConverter(@Qualifier("dynamoObjectMapper") final ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
