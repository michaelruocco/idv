package uk.co.idv.utils.json.converter.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

public class JsonNodeConverter {

    private JsonNodeConverter() {
        // utility class
    }

    public static UUID toUUID(final JsonNode node) {
        return UUID.fromString(node.asText());
    }

    public static Instant toInstant(final JsonNode node) {
        return Instant.parse(node.asText());
    }

    public static Collection<String> toStringCollection(final JsonNode node, final JsonParser parser) {
        return Arrays.asList(toObject(node, parser, String[].class));
    }

    public static <T> T toObject(final JsonNode node, final JsonParser parser, final Class<T> type) {
        try {
            return node.traverse(parser.getCodec()).readValueAs(type);
        } catch (final IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
