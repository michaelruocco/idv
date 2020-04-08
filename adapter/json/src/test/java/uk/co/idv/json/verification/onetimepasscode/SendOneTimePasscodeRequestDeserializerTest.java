package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.SendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.SendOneTimePasscodeRequestMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SendOneTimePasscodeRequestDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/onetimepasscode/send-one-time-passcode-request.json");

        final SendOneTimePasscodeRequest request = MAPPER.readValue(json, SendOneTimePasscodeRequest.class);

        assertThat(request).isEqualToComparingFieldByField(SendOneTimePasscodeRequestMother.build());
    }

}
