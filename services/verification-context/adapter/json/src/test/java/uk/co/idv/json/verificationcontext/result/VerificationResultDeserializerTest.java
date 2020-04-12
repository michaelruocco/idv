package uk.co.idv.json.verificationcontext.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.json.verificationcontext.VerificationContextObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultDeserializerTest {

    private static final ObjectMapper MAPPER = new VerificationContextObjectMapperFactory().build();

    @Test
    void shouldDeserializeSuccessfulVerificationResult() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/result/verification-result-successful.json");

        final VerificationResult result = MAPPER.readValue(json, VerificationResult.class);

        final VerificationResult expectedResult = new FakeVerificationResultSuccessful("push-notification");
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

    @Test
    void shouldDeserializeFailedVerificationResult() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/result/verification-result-failed.json");

        final VerificationResult result = MAPPER.readValue(json, VerificationResult.class);

        final VerificationResult expectedResult = new FakeVerificationResultFailed("push-notification");
        assertThat(result).isEqualToComparingFieldByField(expectedResult);
    }

}
