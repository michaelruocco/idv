package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.VerifyOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.VerifyOneTimePasscodeRequestMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class VerifyOneTimePasscodeRequestDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/onetimepasscode/verify-one-time-passcode-request.json");

        final VerifyOneTimePasscodeRequest request = MAPPER.readValue(json, VerifyOneTimePasscodeRequest.class);

        assertThat(request).isEqualToComparingFieldByField(VerifyOneTimePasscodeRequestMother.build());
    }

}
