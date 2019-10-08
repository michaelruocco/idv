package uk.co.mruoc.idv.lockout.jsonapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.identity.api.IdentityModule;
import uk.co.mruoc.jsonapi.JsonApiModule;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ResetLockoutStateDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("lockout/json-api/reset-lockout-state-document.json");

        final ResetLockoutStateDocument document = MAPPER.readValue(json, ResetLockoutStateDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new FakeResetLockoutStateDocument());
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonApiLockoutStateModule());
        mapper.registerModule(new JsonApiModule());
        mapper.registerModule(new IdentityModule());
        return mapper;
    }

}
