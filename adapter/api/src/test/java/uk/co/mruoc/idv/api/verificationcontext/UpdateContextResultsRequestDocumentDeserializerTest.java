package uk.co.mruoc.idv.api.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.json.activity.ActivityModule;
import uk.co.mruoc.idv.json.channel.ChannelModule;
import uk.co.mruoc.idv.json.identity.IdentityModule;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class UpdateContextResultsRequestDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/json-api/update-context-results-request-document.json");

        final UpdateContextResultsRequestDocument document = MAPPER.readValue(json, UpdateContextResultsRequestDocument.class);

        assertThat(document).usingRecursiveComparison().isEqualTo(new FakeUpdateContextResultsRequestDocument());
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonApiVerificationContextModule());
        mapper.registerModule(new ChannelModule());
        mapper.registerModule(new ActivityModule());
        mapper.registerModule(new IdentityModule());
        return mapper;
    }

}
