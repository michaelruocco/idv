package uk.co.idv.app.config;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
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
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.DynamoConfigFactory;
import uk.co.idv.repository.dynamo.DynamoTableFactory;
import uk.co.idv.repository.dynamo.json.JacksonJsonConverter;
import uk.co.idv.repository.dynamo.json.JsonConverter;

@Configuration
@Profile("!stub")
public class DynamoDaoConfig {

    private final DynamoConfig config = DynamoConfigFactory.standard();

    @Bean
    public AmazonDynamoDB amazonDynamoDB() {
        return config.amazonDynamoDB();
    }

    @Bean
    public IdentityDao identityDao(final DynamoTableFactory tableFactory) {
        return config.identityDao(tableFactory);
    }

    @Bean
    public VerificationContextDao verificationContextDao(final JsonConverter jsonConverter,
                                                         final DynamoTableFactory tableFactory,
                                                         final TimeGenerator timeGenerator) {
        return config.verificationContextDao(jsonConverter, tableFactory, timeGenerator);
    }

    @Bean
    public VerificationAttemptDao verificationAttemptsDao(final JsonConverter jsonConverter,
                                                          final DynamoTableFactory tableFactory) {
        return config.verificationAttemptsDao(jsonConverter, tableFactory);
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter, final DynamoTableFactory tableFactory) {
        return config.lockoutPolicyDao(jsonConverter, tableFactory);
    }

    @Bean
    public DynamoTableFactory tableFactory(final AmazonDynamoDB amazonDynamoDB) {
        return config.tableFactory(amazonDynamoDB);
    }

    @Bean
    public JsonConverter jsonConverter(@Qualifier("dynamoObjectMapper") final ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
