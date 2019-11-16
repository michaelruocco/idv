package uk.co.idv.api.lockout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResetLockoutStateDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.get();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/json-api/reset-lockout-state-document.json");

        final ResetLockoutStateDocument document = MAPPER.readValue(json, ResetLockoutStateDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new FakeResetLockoutStateDocument());
    }

}
