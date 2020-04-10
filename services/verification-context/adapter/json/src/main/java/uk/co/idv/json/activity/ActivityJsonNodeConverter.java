package uk.co.idv.json.activity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.activity.Activity;

import java.io.IOException;
import java.time.Instant;

public interface ActivityJsonNodeConverter {

    boolean supportsActivity(String name);

    Activity toActivity(JsonNode node, JsonParser parser, DeserializationContext context) throws IOException;

    static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    static Instant extractTimestamp(final JsonNode node) {
        return Instant.parse(node.get("timestamp").asText());
    }

}
