package uk.co.idv.json.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.UUID;

public class JsonNodeConverter {

    private JsonNodeConverter() {
        // utility class
    }

    public static UUID toUUID(final JsonNode node) {
        return UUID.fromString(node.asText());
    }

}
