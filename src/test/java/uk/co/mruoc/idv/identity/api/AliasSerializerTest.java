package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.identity.domain.model.Alias;
import uk.co.mruoc.idv.identity.domain.model.FakeCreditCardNumber;


import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class AliasSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeAlias() throws JsonProcessingException {
        final Alias alias = new FakeCreditCardNumber();

        final String json = MAPPER.writeValueAsString(alias);

        final String expectedJson = ContentLoader.loadContentFromClasspath("identity/credit-card-alias.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new IdentityModule());
        return mapper;
    }

}
