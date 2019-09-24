package uk.co.mruoc.jsonapi.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.jsonapi.JsonApiModule;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class JsonApiErrorItemSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeJsonApiErrorItem() throws JsonProcessingException {
        final JsonApiErrorItem item = new FakeInternalServerErrorItem();

        final String json = MAPPER.writeValueAsString(item);

        final String expectedJson = ContentLoader.loadContentFromClasspath("json-api/error/fake-internal-server-error-item.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeJsonApiErrorItemWithMetaData() throws JsonProcessingException {
        final JsonApiErrorItem item = new FakeInternalServerErrorItemWithMeta();

        final String json = MAPPER.writeValueAsString(item);

        final String expectedJson = ContentLoader.loadContentFromClasspath("json-api/error/fake-internal-server-error-item-with-meta.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonApiModule());
        return mapper;
    }

}
