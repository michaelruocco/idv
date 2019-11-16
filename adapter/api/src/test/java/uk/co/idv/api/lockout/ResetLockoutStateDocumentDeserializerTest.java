package uk.co.idv.api.lockout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.jsonapi.ApiModule;

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
        mapper.registerModule(new ApiModule());
        mapper.registerModule(new IdentityModule());
        return mapper;
    }

}