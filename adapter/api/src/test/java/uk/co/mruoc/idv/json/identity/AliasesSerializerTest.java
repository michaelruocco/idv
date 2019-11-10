package uk.co.mruoc.idv.json.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class AliasesSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeAliases() throws JsonProcessingException {
        final Aliases aliases = AliasesMother.aliases();

        final String json = MAPPER.writeValueAsString(aliases);

        final String expectedJson = ContentLoader.loadContentFromClasspath("identity/aliases.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdentityModule());
        return mapper;
    }

}
