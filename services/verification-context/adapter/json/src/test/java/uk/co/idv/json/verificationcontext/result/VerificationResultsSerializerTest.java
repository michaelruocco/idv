package uk.co.idv.json.verificationcontext.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.result.DefaultVerificationResults;
import uk.co.idv.domain.entities.verificationcontext.result.FakeVerificationResultSuccessful;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResult;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.json.verificationcontext.VerificationContextObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationResultsSerializerTest {

    private static final ObjectMapper MAPPER = new VerificationContextObjectMapperFactory().build();

    @Test
    void shouldSerializeVerificationResults() throws JsonProcessingException {
        final VerificationResult result = new FakeVerificationResultSuccessful("push-notification");
        final VerificationResults results = new DefaultVerificationResults(result);

        final String json = MAPPER.writeValueAsString(results);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/result/verification-results.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
