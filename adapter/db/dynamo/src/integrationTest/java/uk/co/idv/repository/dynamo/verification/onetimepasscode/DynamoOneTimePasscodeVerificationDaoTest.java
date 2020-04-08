package uk.co.idv.repository.dynamo.verification.onetimepasscode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.domain.usecases.onetimepasscode.OneTimePasscodeVerificationDao;
import uk.co.idv.domain.usecases.util.CurrentTimeGenerator;
import uk.co.idv.domain.usecases.util.TimeGenerator;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.idv.repository.dynamo.DynamoConfig;
import uk.co.idv.repository.dynamo.DynamoDbLocalContainer;
import uk.co.idv.utils.json.converter.JacksonJsonConverter;
import uk.co.idv.utils.json.converter.JsonConverter;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
class DynamoOneTimePasscodeVerificationDaoTest {

    @Container
    public static final DynamoDbLocalContainer DYNAMO_DB = new DynamoDbLocalContainer();

    private OneTimePasscodeVerificationDao dao;

    @BeforeEach
    void setUp() {
        final DynamoConfig config = DYNAMO_DB.buildConfig();
        final JsonConverter jsonConverter = new JacksonJsonConverter(ObjectMapperSingleton.instance());
        final TimeGenerator timeGenerator = new CurrentTimeGenerator();
        dao = config.oneTimePasscodeVerificationDao(jsonConverter, timeGenerator);
    }

    @Test
    void shouldLoadVerificationVerificationById() {
        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.pending();
        dao.save(expectedVerification);

        final Optional<OneTimePasscodeVerification> loadedVerification = dao.load(expectedVerification.getId());

        assertThat(loadedVerification.isPresent()).isTrue();
        final OneTimePasscodeVerification verification = loadedVerification.get();
        assertThat(verification).isEqualToIgnoringGivenFields(expectedVerification, "deliveries", "attempts");
        assertThat(verification.getDeliveries()).containsExactlyElementsOf(expectedVerification.getDeliveries());
        assertThat(verification.getAttempts()).containsExactlyElementsOf(expectedVerification.getAttempts());
    }

    @Test
    void shouldLoadEmptyOptionalIfVerificationIsNotSaved() {
        final Optional<OneTimePasscodeVerification> loadedVerification = dao.load(UUID.randomUUID());

        assertThat(loadedVerification).isEmpty();
    }

}
