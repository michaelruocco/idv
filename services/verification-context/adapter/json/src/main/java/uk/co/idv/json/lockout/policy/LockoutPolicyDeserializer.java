package uk.co.idv.json.lockout.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.lockout.exception.LockoutTypeNotSupportedException;
import uk.co.idv.domain.entities.lockout.policy.LockoutPolicy;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class LockoutPolicyDeserializer extends StdDeserializer<LockoutPolicy> {

    private final Collection<LockoutPolicyJsonNodeConverter> converters;

    public LockoutPolicyDeserializer(final LockoutPolicyJsonNodeConverter... converters) {
        this(Arrays.asList(converters));
    }

    public LockoutPolicyDeserializer(final Collection<LockoutPolicyJsonNodeConverter> converters) {
        super(LockoutPolicy.class);
        this.converters = converters;
    }

    @Override
    public LockoutPolicy deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String type = LockoutPolicyJsonNodeConverter.extractLockoutType(node);
        return findConverter(type)
                .map(converter -> toPolicy(converter, node, parser, context))
                .orElseThrow(() -> new LockoutTypeNotSupportedException(type));
    }

    private LockoutPolicy toPolicy(final LockoutPolicyJsonNodeConverter converter,
                                   final JsonNode node,
                                   final JsonParser parser,
                                   final DeserializationContext context) {
        try {
            return converter.toPolicy(node, parser, context);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Optional<LockoutPolicyJsonNodeConverter> findConverter(final String type) {
        return converters.stream().filter(converter -> converter.supportsType(type)).findFirst();
    }

}
