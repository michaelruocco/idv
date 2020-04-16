package uk.co.idv.json.identity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.IdentityMother;
import uk.co.idv.utils.json.converter.jackson.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class IdentityMixinTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory(new IdentityModule()).build();

    @Test
    void shouldSerializeIdentity() throws JsonProcessingException {
        final Identity identity = IdentityMother.build();

        final String json = MAPPER.writeValueAsString(identity);

        final String expectedJson = ContentLoader.loadContentFromClasspath("identity/identity.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
