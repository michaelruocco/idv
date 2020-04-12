package uk.co.idv.utils.json.converter.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
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

    public static Collection<String> toStrings(final JsonNode node, final JsonParser parser) throws IOException {
        final String[] values = node.traverse(parser.getCodec()).readValueAs(String[].class);
        return Arrays.asList(values);
    }

}
