package uk.co.idv.repository.dynamo.lockout.attempt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.domain.usecases.lockout.VerificationAttemptDao;
import uk.co.idv.dynamo.test.DynamoDbLocalContainer;
import uk.co.idv.json.VerificationContextModuleProvider;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.utils.json.jackson.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.JsonConverter;
import uk.co.idv.utils.json.jackson.ObjectMapperFactory;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class VerificationAttemptDaoTest {

    @Container
    public static final DynamoDbLocalContainer DYNAMO_DB = new DynamoDbLocalContainer();

    private VerificationAttemptDao dao;

    @BeforeEach
    void setUp() {
        final DynamoConfig config = new DynamoConfig(DYNAMO_DB.buildClient());
        final ObjectMapperFactory factory = new ObjectMapperFactory(new VerificationContextModuleProvider());
        final JsonConverter jsonConverter = new JacksonJsonConverter(factory.build());
        dao = config.verificationAttemptDao(jsonConverter);
    }

    @Test
    void shouldLoadVerificationAttemptsByIdvId() {
        final VerificationAttempts attempts = VerificationAttemptsMother.withNumberOfAttempts(2);
        dao.save(attempts);

        final Optional<VerificationAttempts> loadedAttempts = dao.load(attempts.getIdvId());

        assertThat(loadedAttempts.isPresent()).isTrue();
        assertThat(loadedAttempts.get()).containsExactlyElementsOf(attempts);
    }

    @Test
    void shouldLoadEmptyOptionalIfAttemptsAreNotSaved() {
        final Optional<VerificationAttempts> loadedAttempts = dao.load(UUID.randomUUID());

        assertThat(loadedAttempts).isEmpty();
    }

}
