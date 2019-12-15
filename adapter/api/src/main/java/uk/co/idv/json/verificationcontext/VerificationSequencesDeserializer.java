package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class VerificationSequencesDeserializer extends StdDeserializer<VerificationSequences> {

    public VerificationSequencesDeserializer() {
        super(VerificationSequences.class);
    }

    @Override
    public VerificationSequences deserialize(final JsonParser parser,final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final Collection<VerificationSequence> sequences = Arrays.asList(node.traverse(parser.getCodec()).readValueAs(VerificationSequence[].class));
        return new VerificationSequences(sequences);
    }

}
