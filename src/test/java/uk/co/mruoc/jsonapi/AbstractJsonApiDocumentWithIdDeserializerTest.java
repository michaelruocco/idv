package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.jsonapi.fake.FakeJsonApiDocumentWithId;
import uk.co.mruoc.jsonapi.fake.FakeJsonApiModule;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class AbstractJsonApiDocumentWithIdDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeJsonApiDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("json-api/fake-attributes-document-with-id.json");

        final FakeJsonApiDocumentWithId document = MAPPER.readValue(json, FakeJsonApiDocumentWithId.class);

        assertThatJson(document).isEqualTo(new FakeJsonApiDocumentWithId());
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new FakeJsonApiModule());
        return mapper;
    }

}
