package uk.co.idv.json.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.AliasesMother;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static org.assertj.core.api.Assertions.assertThat;

class IdentityDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory().build();

    @Test
    void shouldDeserializeIdentity() throws JsonProcessingException {
        final String json = ContentLoader.loadContentFromClasspath("identity/identity.json");

        final Identity identity = MAPPER.readValue(json, Identity.class);

        final Identity expectedIdentity = new Identity(AliasesMother.aliases());
        assertThat(identity.getAliases()).containsExactlyElementsOf(expectedIdentity.getAliases());
    }

}
