package uk.co.idv.json.verificationcontext.sequence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequences;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class VerificationSequencesDeserializer extends StdDeserializer<VerificationSequences> {

    public VerificationSequencesDeserializer() {
        super(VerificationSequences.class);
    }

    @Override
    public VerificationSequences deserialize(final JsonParser parser,final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final VerificationSequence[] sequences = JsonNodeConverter.toObject(node, parser, VerificationSequence[].class);
        return new VerificationSequences(sequences);
    }

}
