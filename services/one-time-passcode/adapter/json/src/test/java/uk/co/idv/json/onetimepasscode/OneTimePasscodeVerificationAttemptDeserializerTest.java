package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttemptMother;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class OneTimePasscodeVerificationAttemptDeserializerTest {

    private static final ObjectMapper MAPPER = new OneTimePasscodeObjectMapperFactory().build();

    @Test
    void shouldDeserializeAttempt() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("one-time-passcode-verification-attempt.json");

        final OneTimePasscodeVerificationAttempt attempt = MAPPER.readValue(json, OneTimePasscodeVerificationAttempt.class);

        assertThat(attempt).isEqualToComparingFieldByField(OneTimePasscodeVerificationAttemptMother.attempt());
    }

}
