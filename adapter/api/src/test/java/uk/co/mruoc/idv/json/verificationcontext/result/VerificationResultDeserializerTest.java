package uk.co.mruoc.idv.json.verificationcontext.result;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.json.verificationcontext.VerificationContextModule;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultFailed;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import java.io.IOException;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.assertj.core.api.Assertions.assertThat;

class VerificationResultDeserializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

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

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
