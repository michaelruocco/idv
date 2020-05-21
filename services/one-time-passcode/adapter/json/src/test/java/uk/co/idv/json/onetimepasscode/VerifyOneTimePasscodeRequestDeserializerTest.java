package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequest;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequestMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class VerifyOneTimePasscodeRequestDeserializerTest {

    private static final ObjectMapper MAPPER = new OneTimePasscodeObjectMapperFactory().build();

    @Test
    void shouldDeserializeRequest() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verify-one-time-passcode-request.json");

        final VerifyOneTimePasscodeRequest request = MAPPER.readValue(json, VerifyOneTimePasscodeRequest.class);

        assertThat(request).isEqualToComparingFieldByField(VerifyOneTimePasscodeRequestMother.build());
    }

}
