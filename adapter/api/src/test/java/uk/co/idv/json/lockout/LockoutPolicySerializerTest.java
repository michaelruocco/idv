package uk.co.idv.json.lockout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutPolicySerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeHardLockPolicyParameters() throws JsonProcessingException {
        final LockoutPolicyParameters parameters = LockoutPolicyParametersMother.hardLock();

        final String json = MAPPER.writeValueAsString(parameters);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/hard/hard-alias-level-lockout-policy.json");
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
