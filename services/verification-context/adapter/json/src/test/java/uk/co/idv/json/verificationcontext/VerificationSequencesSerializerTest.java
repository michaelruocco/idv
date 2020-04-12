package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.StubVerificationSequencesEligible;
import uk.co.idv.domain.entities.verificationcontext.StubVerificationSequencesIneligible;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationSequencesSerializerTest {

    private static final ObjectMapper MAPPER = new VerificationContextObjectMapperFactory().build();

    @Test
    void shouldSerializeSequences() throws JsonProcessingException {
        final VerificationSequences sequences = new StubVerificationSequencesEligible();

        final String json = MAPPER.writeValueAsString(sequences);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-sequences.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleSequence() throws JsonProcessingException {
        final VerificationSequences sequences = new StubVerificationSequencesIneligible();

        final String json = MAPPER.writeValueAsString(sequences);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/verification-sequences-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
