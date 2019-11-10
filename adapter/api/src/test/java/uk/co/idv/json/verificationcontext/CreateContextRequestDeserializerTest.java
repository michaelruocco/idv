package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.zalando.jackson.datatype.money.MoneyModule;
import uk.co.idv.json.activity.ActivityModule;
import uk.co.idv.json.channel.ChannelModule;
import uk.co.idv.json.identity.IdentityModule;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.idv.domain.usecases.verificationcontext.CreateContextRequest;
import uk.co.idv.domain.usecases.verificationcontext.FakeCreateContextRequest;

import java.io.IOException;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.assertj.core.api.Assertions.assertThat;

class CreateContextRequestDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldDeserializeCreateContextRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/create-context-request.json");

        final CreateContextRequest request = MAPPER.readValue(json, CreateContextRequest.class);

        assertThat(request).isEqualToComparingFieldByField(new FakeCreateContextRequest());
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        mapper.registerModule(new ChannelModule());
        mapper.registerModule(new ActivityModule());
        mapper.registerModule(new IdentityModule());
        mapper.registerModule(new MoneyModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
