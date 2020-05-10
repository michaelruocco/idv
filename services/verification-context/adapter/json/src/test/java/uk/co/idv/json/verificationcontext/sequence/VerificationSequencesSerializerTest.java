package uk.co.idv.json.verificationcontext.sequence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequenceMother;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;
import uk.co.idv.json.verificationcontext.VerificationContextObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class VerificationSequencesSerializerTest {

    private static final ObjectMapper MAPPER = new VerificationContextObjectMapperFactory().build();

    @Test
    void shouldSerializeEligibleSequences() throws JsonProcessingException {
        final VerificationSequences sequences = VerificationSequenceMother.defaultEligibleMethodSequences();

        final String json = MAPPER.writeValueAsString(sequences);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/sequence/verification-sequences.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

    @Test
    void shouldSerializeIneligibleSequence() throws JsonProcessingException {
        final VerificationSequences sequences = VerificationSequenceMother.defaultIneligibleMethodSequences();

        final String json = MAPPER.writeValueAsString(sequences);

        final String expectedJson = ContentLoader.loadContentFromClasspath("verification-context/sequence/verification-sequences-ineligible.json");
        assertThatJson(json).isEqualTo(expectedJson);
    }

}
