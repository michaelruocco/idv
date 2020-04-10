package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;
import uk.co.idv.domain.usecases.verificationcontext.FakeCreateContextRequest;
import uk.co.idv.json.TestObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CreateContextRequestDeserializerTest {

    private static final ObjectMapper MAPPER = TestObjectMapperFactory.build();

    @Test
    void shouldDeserializeRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/create-context-request.json");

        final CreateContextRequest request = MAPPER.readValue(json, CreateContextRequest.class);

        assertThat(request).isEqualToComparingFieldByField(new FakeCreateContextRequest());
    }

}
