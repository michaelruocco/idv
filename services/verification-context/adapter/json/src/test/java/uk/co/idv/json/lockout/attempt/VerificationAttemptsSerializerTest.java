package uk.co.idv.json.lockout.attempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationAttemptsSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new VerificationAttemptsModule()).build();

    @Test
    void shouldSerializeAttempts() throws JsonProcessingException {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();

        final String json = MAPPER.writeValueAsString(attempts);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/attempt/verification-attempts.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
