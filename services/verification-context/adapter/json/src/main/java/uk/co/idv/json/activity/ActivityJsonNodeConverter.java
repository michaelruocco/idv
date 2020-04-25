package uk.co.idv.json.activity;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;

public class ActivityJsonNodeConverter {

    private ActivityJsonNodeConverter() {
        // utility class
    }

    public static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    public static Instant extractTimestamp(final JsonNode node) {
        return Instant.parse(node.get("timestamp").asText());
    }

}
