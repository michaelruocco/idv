package uk.co.idv.api.lockout.policy.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ApiTestObjectMapperFactory;
import uk.co.idv.api.lockout.state.LockoutStateDocument;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class LockoutStateDocumentSerializerTest {

    private static final ObjectMapper MAPPER = ApiTestObjectMapperFactory.build();

    @Test
    void shouldSerializeDocument() throws IOException {
        final LockoutStateDocument document = new FakeLockoutStateDocument();

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("lockout/lockout-state-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
