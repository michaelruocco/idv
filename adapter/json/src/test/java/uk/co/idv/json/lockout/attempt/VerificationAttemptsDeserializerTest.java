package uk.co.idv.json.lockout.attempt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttemptsMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationAttemptsDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeAttempts() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/attempt/verification-attempts.json");

        final VerificationAttempts attempts = MAPPER.readValue(json, VerificationAttempts.class);

        final VerificationAttempts expectedAttempts = VerificationAttemptsMother.oneAttempt();
        assertThat(attempts.getId()).isEqualTo(expectedAttempts.getId());
        assertThat(attempts.getIdvId()).isEqualTo(expectedAttempts.getIdvId());
        assertThat(attempts).containsExactlyElementsOf(expectedAttempts);
    }

}
