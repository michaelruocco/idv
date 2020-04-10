package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.MultipleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.SingleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class VerificationSequenceDeserializer extends StdDeserializer<VerificationSequence> {

    public VerificationSequenceDeserializer() {
        super(VerificationSequence.class);
    }

    @Override
    public VerificationSequence deserialize(final JsonParser parser,final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final JsonNode methods = node.get("methods");
        if (methods.size() == 1) {
            return new SingleMethodSequence(toMethod(parser, methods.get(0)));
        }
        return new MultipleMethodSequence(extractName(node), toMethods(parser, methods));
    }

    private static VerificationMethod toMethod(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(VerificationMethod.class);
    }

    private static Collection<VerificationMethod> toMethods(final JsonParser parser, final JsonNode node) throws IOException {
        return Arrays.asList(node.traverse(parser.getCodec()).readValueAs(VerificationMethod[].class));
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

}
