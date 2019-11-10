package uk.co.idv.json.lockout;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.lockout.state.FakeLockoutStateMaxAttempts;
import uk.co.idv.domain.entities.lockout.state.LockoutState;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutStateMaxAttemptsMixinTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeLockoutStateMaxAttempts() throws JsonProcessingException {
        final LockoutState state = new FakeLockoutStateMaxAttempts();

        final String json = MAPPER.writeValueAsString(state);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/max-attempts-lockout-state.json");
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
