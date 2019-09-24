package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.jsonapi.fake.FakeAttributes;
import uk.co.mruoc.jsonapi.fake.FakeJsonApiDocumentWithId;
import uk.co.mruoc.jsonapi.fake.FakeJsonApiModule;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class AbstractJsonApiDocumentWithIdSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeJsonApiDocumentWithId() throws JsonProcessingException {
        final JsonApiDocumentWithId<FakeAttributes> document = new FakeJsonApiDocumentWithId();

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("json-api/fake-attributes-document-with-id.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new FakeJsonApiModule());
        return mapper;
    }

}
