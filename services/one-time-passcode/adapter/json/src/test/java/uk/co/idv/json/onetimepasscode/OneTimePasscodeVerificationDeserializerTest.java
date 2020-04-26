package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OneTimePasscodeVerificationDeserializerTest {

    private static final ObjectMapper MAPPER = new OneTimePasscodeObjectMapperFactory().build();

    @Test
    void shouldDeserializePendingVerification() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("one-time-passcode-verification-pending.json");

        final OneTimePasscodeVerification verification = MAPPER.readValue(json, OneTimePasscodeVerification.class);

        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.pending();
        assertThat(verification).isEqualToIgnoringGivenFields(expectedVerification, "deliveries", "attempts");
        assertThat(verification.getDeliveries()).containsExactlyElementsOf(expectedVerification.getDeliveries());
        assertThat(verification.getAttempts()).containsExactlyElementsOf(expectedVerification.getAttempts());
    }

    @Test
    void shouldDeserializeCompleteVerification() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("one-time-passcode-verification-successful.json");

        final OneTimePasscodeVerification verification = MAPPER.readValue(json, OneTimePasscodeVerification.class);

        final OneTimePasscodeVerification expectedVerification = OneTimePasscodeVerificationMother.successful();
        assertThat(verification).isEqualToIgnoringGivenFields(expectedVerification, "deliveries", "attempts");
        assertThat(verification.getDeliveries()).containsExactlyElementsOf(expectedVerification.getDeliveries());
        assertThat(verification.getAttempts()).containsExactlyElementsOf(expectedVerification.getAttempts());
    }

}
