package uk.co.idv.json.identity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.alias.Aliases;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class AliasesDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new AliasModule()).build();

    @Test
    void shouldDeserializeAliases() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("identity/aliases.json");

        final Aliases aliases = MAPPER.readValue(json, Aliases.class);

        final Aliases expectedAliases = AliasesMother.aliases();
        assertThat(aliases).containsExactlyElementsOf(expectedAliases);
    }

}
