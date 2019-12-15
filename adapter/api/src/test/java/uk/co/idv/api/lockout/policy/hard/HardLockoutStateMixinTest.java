package uk.co.idv.api.lockout.policy.hard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.JsonApiObjectMapperSingleton;
import uk.co.idv.api.lockout.state.LockoutStateDocument;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.lockout.policy.hard.FakeHardLockoutState;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class HardLockoutStateMixinTest {

    private static final ObjectMapper MAPPER = JsonApiObjectMapperSingleton.instance();

    @Test
    void shouldSerializeDocument() throws IOException {
        final LockoutStateDocument document = new LockoutStateDocument(new FakeHardLockoutState());

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/hard/hard-lockout-state-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
