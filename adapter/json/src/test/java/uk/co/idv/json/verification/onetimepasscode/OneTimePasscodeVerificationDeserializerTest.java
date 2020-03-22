package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OneTimePasscodeVerificationDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeVerification() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/onetimepasscode/one-time-passcode-verification-pending.json");

        final OneTimePasscodeVerification verification = MAPPER.readValue(json, OneTimePasscodeVerification.class);

        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.pending();
        assertThat(verification).isEqualToIgnoringGivenFields(expectedVerification, "deliveries", "attempts");
        assertThat(verification.getDeliveries()).containsExactlyElementsOf(expectedVerification.getDeliveries());
        assertThat(verification.getAttempts()).containsExactlyElementsOf(expectedVerification.getAttempts());
    }

}
