package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.SingleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.StubVerificationSequencesEligible;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.idv.domain.entities.verificationcontext.method.FakeVerificationMethodIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.mruoc.file.content.ContentLoader;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationSequencesSerializerTest {

    private static final ObjectMapper MAPPER = buildMapper();

    @Test
    void shouldSerializeSequences() throws JsonProcessingException {
        final VerificationSequences sequences = new StubVerificationSequencesEligible();

        final String json = MAPPER.writeValueAsString(sequences);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-sequences.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleSequence() throws JsonProcessingException {
        final VerificationMethod method = new FakeVerificationMethodIneligible();
        final VerificationSequence sequence = new SingleMethodSequence(method);
        final VerificationSequences sequences = new VerificationSequences(sequence);

        final String json = MAPPER.writeValueAsString(sequences);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-sequence-ineligible.json");
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
