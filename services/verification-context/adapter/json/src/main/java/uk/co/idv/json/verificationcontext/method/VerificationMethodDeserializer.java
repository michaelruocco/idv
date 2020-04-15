package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.exception.MethodNotSupportedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class VerificationMethodDeserializer extends StdDeserializer<VerificationMethod> {

    private final Collection<VerificationMethodJsonNodeConverter> converters;

    public VerificationMethodDeserializer(final VerificationMethodJsonNodeConverter... converters) {
        this(Arrays.asList(converters));
    }

    public VerificationMethodDeserializer(final Collection<VerificationMethodJsonNodeConverter> converters) {
        super(VerificationMethod.class);
        this.converters = converters;
    }

    @Override
    public VerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String name = node.get("name").asText();
        return findConverter(name)
                .map(converter -> converter.toMethod(node, parser, context))
                .orElseThrow(() -> new MethodNotSupportedException(name));
    }

    private Optional<VerificationMethodJsonNodeConverter> findConverter(final String name) {
        return converters.stream().filter(converter -> converter.supportsMethod(name)).findFirst();
    }

}
