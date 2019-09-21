package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import uk.co.mruoc.file.content.ContentLoader;
import uk.co.mruoc.idv.verificationcontext.domain.model.FakeVerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.SingleMethodSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequence;
import uk.co.mruoc.idv.verificationcontext.domain.model.VerificationSequences;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.FakeVerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.method.VerificationMethod;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.FakeVerificationResultSuccessful;
import uk.co.mruoc.idv.verificationcontext.domain.model.result.VerificationResult;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationSequencesSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeSequences() throws JsonProcessingException {
        final VerificationSequences sequences = new FakeVerificationSequences();

        final String json = MAPPER.writeValueAsString(sequences);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-sequences.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeSequenceWithResult() throws JsonProcessingException {
        final VerificationMethod method = new FakeVerificationMethod();
        final VerificationResult result = new FakeVerificationResultSuccessful(method.getName());
        final VerificationSequence sequence = new SingleMethodSequence(method, result);
        final VerificationSequences sequences = new VerificationSequences(sequence);

        final String json = MAPPER.writeValueAsString(sequences);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-sequence-with-result.json");
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
