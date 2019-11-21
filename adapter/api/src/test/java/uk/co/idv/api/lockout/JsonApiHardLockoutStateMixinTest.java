package uk.co.idv.api.lockout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockoutState;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class JsonApiHardLockoutStateMixinTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    void shouldSerializeDocument() throws IOException {
        final LockoutStateDocument document = new LockoutStateDocument(new FakeHardLockoutState());

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/json-api/hard/hard-lockout-state-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
