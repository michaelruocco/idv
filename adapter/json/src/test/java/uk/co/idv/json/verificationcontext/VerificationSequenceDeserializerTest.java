package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequenceMother;
import uk.co.idv.json.ObjectMapperFactory;
import uk.co.mruoc.file.content.ContentLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class VerificationSequenceDeserializerTest {

    private static final ObjectMapper MAPPER = new ObjectMapperFactory().build();

    @Test
    void shouldDeserializeSequenceWithSingleMethod() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/single-method-sequence.json");

        final VerificationSequence sequence = MAPPER.readValue(json, VerificationSequence.class);

        final VerificationSequence expectedSequence = VerificationSequenceMother.singleMethodSequence();
        assertThat(sequence).isEqualTo(expectedSequence);
    }

    @Test
    void shouldDeserializeSequenceWithMultipleMethods() throws IOException {
        final String json = ContentLoader.loadContentFromClasspath("verification-context/multiple-method-sequence.json");

        final VerificationSequence sequence = MAPPER.readValue(json, VerificationSequence.class);

        final VerificationSequence expectedSequence = VerificationSequenceMother.multipleMethodSequence();
        assertThat(sequence).isEqualToIgnoringGivenFields(expectedSequence, "methods");
        assertThat(sequence.getMethods()).containsExactlyElementsOf(expectedSequence.getMethods());
    }

}
