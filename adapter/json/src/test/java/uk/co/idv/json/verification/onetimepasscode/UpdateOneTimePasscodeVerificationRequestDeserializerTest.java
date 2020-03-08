package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequest;
import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequestMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateOneTimePasscodeVerificationRequestDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/onetimepasscode/update-one-time-passcode-verification-request.json");

        final UpdateOneTimePasscodeVerificationRequest request = MAPPER.readValue(json, UpdateOneTimePasscodeVerificationRequest.class);

        assertThat(request).isEqualToComparingFieldByField(UpdateOneTimePasscodeVerificationRequestMother.build());
    }

}
