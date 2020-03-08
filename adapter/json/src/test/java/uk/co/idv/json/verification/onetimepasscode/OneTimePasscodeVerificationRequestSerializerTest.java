package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class OneTimePasscodeVerificationRequestSerializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializePendingVerification() throws IOException {
        final OneTimePasscodeVerification verification = OneTimePasscodeVerificationMother.pending();

        final String json = MAPPER.writeValueAsString(verification);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification/onetimepasscode/one-time-passcode-verification-pending.json");
        System.out.println(json);
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
