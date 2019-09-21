package uk.co.mruoc.idv.identity.domain.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.identity.api.IdentityModule;
import uk.co.mruoc.idv.identity.domain.model.FakeIdentity;
import uk.co.mruoc.idv.identity.domain.model.Identity;

import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class IdentityModuleTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeIdentity() throws JsonProcessingException {
        final Identity identity = new FakeIdentity(UUID.fromString("582de75b-d207-4d70-81ea-1be9bd326a28"));

        final String json = MAPPER.writeValueAsString(identity);

        final String expectedJson = ContentLoader.loadContentFromClasspath("identity/identity.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdentityModule());
        return mapper;
    }

}
