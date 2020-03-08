package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.verification.onetimepasscode.CreateOneTimePasscodeVerificationRequest;
import uk.co.idv.domain.usecases.verification.onetimepasscode.CreateOneTimePasscodeVerificationRequestMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOneTimePasscodeVerificationRequestDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/onetimepasscode/create-one-time-passcode-verification-request.json");

        final CreateOneTimePasscodeVerificationRequest request = MAPPER.readValue(json, CreateOneTimePasscodeVerificationRequest.class);

        assertThat(request).isEqualToComparingFieldByField(CreateOneTimePasscodeVerificationRequestMother.build());
    }

}
