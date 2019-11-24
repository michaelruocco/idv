package uk.co.idv.api.lockout.attempts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ObjectMapperSingleton;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationAttemptsSerializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldSerializeAttempts() throws JsonProcessingException {
        final VerificationAttempts attempts = VerificationAttemptsMother.oneAttempt();

        final String json = MAPPER.writeValueAsString(attempts);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/verification-attempts.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
