package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class OneTimePasscodeVerificationRequestSerializerTest {

    private static final ObjectMapper MAPPER = new OneTimePasscodeObjectMapperFactory().build();

    @Test
    void shouldSerializePendingVerification() throws IOException {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final String json = MAPPER.writeValueAsString(verification);

        final String expectedJson = ContentLoader.loadContentFromClasspath("one-time-passcode-verification-pending.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
