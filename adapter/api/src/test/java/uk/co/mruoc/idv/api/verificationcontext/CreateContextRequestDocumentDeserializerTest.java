package uk.co.mruoc.idv.api.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.json.activity.ActivityModule;
import uk.co.mruoc.idv.json.channel.ChannelModule;
import uk.co.mruoc.idv.json.identity.IdentityModule;
import uk.co.mruoc.idv.verificationcontext.domain.service.FakeCreateContextRequest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CreateContextRequestDocumentDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeDocument() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/json-api/create-context-request-document.json");

        final CreateContextRequestDocument document = MAPPER.readValue(json, CreateContextRequestDocument.class);

        assertThat(document).isEqualToComparingFieldByField(new CreateContextRequestDocument(new FakeCreateContextRequest()));
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
