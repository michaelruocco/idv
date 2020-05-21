package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.send.SendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.send.SendOneTimePasscodeRequestMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SendOneTimePasscodeRequestDeserializerTest {

    private static final ObjectMapper MAPPER = new OneTimePasscodeObjectMapperFactory().build();

    @Test
    void shouldDeserializeRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("send-one-time-passcode-request.json");

        final SendOneTimePasscodeRequest request = MAPPER.readValue(json, SendOneTimePasscodeRequest.class);

        assertThat(request).isEqualToComparingFieldByField(SendOneTimePasscodeRequestMother.build());
    }

}
