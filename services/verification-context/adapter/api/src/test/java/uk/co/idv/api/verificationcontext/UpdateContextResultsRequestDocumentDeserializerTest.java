package uk.co.idv.api.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateContextResultsRequestDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = new ApiVerificationContextObjectMapperFactory().build();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/update-context-results-request-document.json");

        final UpdateContextResultsRequestDocument document = MAPPER.readValue(json, UpdateContextResultsRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new FakeUpdateContextResultsRequestDocument());
    }

}
