package uk.co.idv.json.verificationcontext.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultFailed;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.json.verificationcontext.VerificationContextModule;
import uk.co.mruoc.file.content.ContentLoader;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationResultSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeSuccessfulVerificationResult() throws JsonProcessingException {
        final VerificationResult result = new FakeVerificationResultSuccessful("push-notification");

        final String json = MAPPER.writeValueAsString(result);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/result/verification-result-successful.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeFailedVerificationResult() throws JsonProcessingException {
        final VerificationResult result = new FakeVerificationResultFailed("push-notification");

        final String json = MAPPER.writeValueAsString(result);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/result/verification-result-failed.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    private static ObjectMapper buildMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new VerificationContextModule());
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

}
