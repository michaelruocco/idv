package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.api.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateContextResultsRequestDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/json-api/update-context-results-request-document.json");

        final UpdateContextResultsRequestDocument document = MAPPER.readValue(json, UpdateContextResultsRequestDocument.class);

        assertThat(document).usingRecursiveComparison().isEqualTo(new FakeUpdateContextResultsRequestDocument());
    }

}
