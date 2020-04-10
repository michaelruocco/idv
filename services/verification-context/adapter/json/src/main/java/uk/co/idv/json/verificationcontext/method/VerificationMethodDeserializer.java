package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.usecases.exception.MethodNotSupportedException;

import java.io.IOException;
import java.io.UncheckedIOException;
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
                .map(converter -> toMethod(converter, node, parser, context))
                .orElseThrow(() -> new MethodNotSupportedException(name));
    }

    private VerificationMethod toMethod(final VerificationMethodJsonNodeConverter converter,
                                        final JsonNode node,
                                        final JsonParser parser,
                                        final DeserializationContext context) {
        try {
            return converter.toMethod(node, parser, context);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Optional<VerificationMethodJsonNodeConverter> findConverter(final String name) {
        return converters.stream().filter(converter -> converter.supportsMethod(name)).findFirst();
    }

}
