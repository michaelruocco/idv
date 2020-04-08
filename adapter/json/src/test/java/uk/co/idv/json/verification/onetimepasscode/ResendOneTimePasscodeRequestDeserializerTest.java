package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.ResendOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.ResendOneTimePasscodeRequestMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class ResendOneTimePasscodeRequestDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/onetimepasscode/resend-one-time-passcode-request.json");

        final ResendOneTimePasscodeRequest request = MAPPER.readValue(json, ResendOneTimePasscodeRequest.class);

        assertThat(request).isEqualToComparingFieldByField(ResendOneTimePasscodeRequestMother.build());
    }

}
