package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.identity.domain.model.Aliases;
import uk.co.mruoc.idv.identity.domain.model.AliasesMother;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class AliasesDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeAliases() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("identity/aliases.json");

        final Aliases aliases = MAPPER.readValue(json, Aliases.class);

        final Aliases expectedAliases = AliasesMother.aliases();
        assertThat(aliases).containsExactlyElementsOf(expectedAliases);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdentityModule());
        return mapper;
    }

}
