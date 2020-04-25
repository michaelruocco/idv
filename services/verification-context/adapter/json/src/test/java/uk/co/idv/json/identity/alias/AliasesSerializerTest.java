package uk.co.idv.json.identity.alias;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class AliasesSerializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new AliasModule()).build();

    @Test
    void shouldSerializeAliases() throws JsonProcessingException {
        final Aliases aliases = AliasesMother.aliases();

        final String json = MAPPER.writeValueAsString(aliases);

        final String expectedJson = ContentLoader.loadContentFromClasspath("identity/aliases.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
