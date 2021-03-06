package uk.co.idv.json.verificationcontext.sequence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequenceMother;
import uk.co.idv.json.verificationcontext.VerificationContextObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationSequenceDeserializerTest {

    private static final ObjectMapper MAPPER = new VerificationContextObjectMapperFactory().build();

    @Test
    void shouldDeserializeSequenceWithSingleMethod() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/sequence/single-method-sequence.json");

        final VerificationSequence sequence = MAPPER.readValue(json, VerificationSequence.class);

        final VerificationSequence expectedSequence = VerificationSequenceMother.oneEligibleMethodSequence();
        assertThat(sequence).isEqualTo(expectedSequence);
    }

    @Test
    void shouldDeserializeSequenceWithMultipleMethods() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/sequence/multiple-method-sequence.json");

        final VerificationSequence sequence = MAPPER.readValue(json, VerificationSequence.class);

        final VerificationSequence expectedSequence = VerificationSequenceMother.twoEligibleMethodSequence();
        assertThat(sequence).isEqualToIgnoringGivenFields(expectedSequence, "methods");
        assertThat(sequence.getMethods()).containsExactlyElementsOf(expectedSequence.getMethods());
    }

}
