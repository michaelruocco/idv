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
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.DynamoConfigFactory;
import uk.co.idv.repository.dynamo.IdvTables;
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
    public IdvTables idvTables(final AmazonDynamoDB dynamoDB) {
        return config.idvTables(dynamoDB);
    }

    @Bean
    public IdentityDao identityDao(final IdvTables tables) {
        return config.identityDao(tables);
    }

    @Bean
    public VerificationContextDao verificationContextDao(final JsonConverter jsonConverter, final IdvTables tables) {
        return config.verificationContextDao(jsonConverter, tables);
    }

    @Bean
    public VerificationAttemptDao verificationAttemptsDao(final JsonConverter jsonConverter, final IdvTables tables) {
        return config.verificationAttemptsDao(jsonConverter, tables);
    }

    @Bean
    public LockoutPolicyDao lockoutPolicyDao(final JsonConverter jsonConverter, final IdvTables tables) {
        return config.lockoutPolicyDao(jsonConverter, tables);
    }

    @Bean
    public JsonConverter jsonConverter(@Qualifier("dynamoObjectMapper") final ObjectMapper mapper) {
        return new JacksonJsonConverter(mapper);
    }

}
