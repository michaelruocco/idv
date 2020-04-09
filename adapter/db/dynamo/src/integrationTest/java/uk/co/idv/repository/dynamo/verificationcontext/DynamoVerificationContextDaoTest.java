package uk.co.idv.repository.dynamo.verificationcontext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.activity.ActivityMother;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationContextMother;
import uk.co.idv.domain.usecases.util.CurrentTimeGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.domain.usecases.verificationcontext.VerificationContextDao;
import uk.co.idv.dynamo.test.DynamoDbLocalContainer;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.utils.json.converter.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class DynamoVerificationContextDaoTest {

    @Container
    public static final DynamoDbLocalContainer DYNAMO_DB = new DynamoDbLocalContainer();

    private VerificationContextDao dao;

    @BeforeEach
    void setUp() {
        final DynamoConfig config = new DynamoConfig(DYNAMO_DB.buildClient());
        final JsonConverter jsonConverter = new JacksonJsonConverter(ObjectMapperSingleton.instance());
        final TimeGenerator timeGenerator = new CurrentTimeGenerator();
        dao = config.verificationContextDao(jsonConverter, timeGenerator);
    }

    @Test
    void shouldLoadVerificationContextById() {
        final VerificationContext context = VerificationContextMother.withActivity(ActivityMother.onlinePurchase());
        dao.save(context);

        final Optional<VerificationContext> loadedContext = dao.load(context.getId());

        assertThat(loadedContext.isPresent()).isTrue();
        assertThat(loadedContext.get()).isEqualToComparingFieldByField(context);
    }

    @Test
    void shouldLoadEmptyOptionalIfContextIsNotSaved() {
        final Optional<VerificationContext> loadedContext = dao.load(UUID.randomUUID());

        assertThat(loadedContext).isEmpty();
    }

}
