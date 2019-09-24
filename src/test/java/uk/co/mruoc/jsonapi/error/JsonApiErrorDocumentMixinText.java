package uk.co.mruoc.jsonapi.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.jsonapi.JsonApiModule;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class JsonApiErrorDocumentMixinText {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeJsonApiSingleErrorDocument() throws JsonProcessingException {
        final JsonApiErrorDocument document = new FakeSingleErrorDocument();

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("json-api/error/single-error-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeJsonApiMultipleErrorDocument() throws JsonProcessingException {
        final JsonApiErrorDocument document = new FakeMultipleErrorDocument();

        final String json = MAPPER.writeValueAsString(document);

        final String expectedJson = ContentLoader.loadContentFromClasspath("json-api/error/multiple-error-document.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonApiModule());
        return mapper;
    }

}
