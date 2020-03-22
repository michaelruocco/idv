package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.entities.verification.onetimepasscode.OneTimePasscodeVerificationAttemptMother;
import uk.co.idv.json.ObjectMapperSingleton;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OneTimePasscodeVerificationAttemptDeserializerTest {

    private static final ObjectMapper MAPPER = ObjectMapperSingleton.instance();

    @Test
    void shouldDeserializeAttempt() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification/onetimepasscode/one-time-passcode-verification-attempt.json");

        final OneTimePasscodeVerificationAttempt attempt = MAPPER.readValue(json, OneTimePasscodeVerificationAttempt.class);

        assertThat(attempt).isEqualToComparingFieldByField(OneTimePasscodeVerificationAttemptMother.attempt());
    }

}
