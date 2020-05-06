package uk.co.idv.json.verificationcontext.sequence;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.sequence.MultipleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.sequence.SingleMethodSequence;
import uk.co.idv.domain.entities.verificationcontext.sequence.VerificationSequence;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Arrays;
import java.util.Collection;

public class VerificationSequenceDeserializer extends StdDeserializer<VerificationSequence> {

    public VerificationSequenceDeserializer() {
        super(VerificationSequence.class);
    }

    @Override
    public VerificationSequence deserialize(final JsonParser parser,final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final JsonNode methods = node.get("methods");
        if (methods.size() == 1) {
            return new SingleMethodSequence(toMethod(parser, methods.get(0)));
        }
        return new MultipleMethodSequence(extractName(node), toMethods(parser, methods));
    }

    private static VerificationMethod toMethod(final JsonParser parser, final JsonNode node) {
        return JsonNodeConverter.toObject(node, parser, VerificationMethod.class);
    }

    private static Collection<VerificationMethod> toMethods(final JsonParser parser, final JsonNode node) {
        VerificationMethod[] methods = JsonNodeConverter.toObject(node, parser, VerificationMethod[].class);
        return Arrays.asList(methods);
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

}
