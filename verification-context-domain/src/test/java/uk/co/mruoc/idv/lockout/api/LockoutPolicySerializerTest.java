package uk.co.mruoc.idv.lockout.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.lockout.domain.model.FakeMaxAttemptsLockoutPolicyParameters;
import uk.co.mruoc.idv.lockout.domain.model.LockoutPolicyParameters;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutPolicySerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeMaxAttemptsPolicyParameters() throws JsonProcessingException {
        final LockoutPolicyParameters parameters = new FakeMaxAttemptsLockoutPolicyParameters();

        final String json = MAPPER.writeValueAsString(parameters);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/max-attempts-lockout-policy.json");
        System.out.println(json);
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new LockoutStateModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
