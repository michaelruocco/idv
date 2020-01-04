package uk.co.idv.json.activity;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.activity.Activity;

import java.time.Instant;

public interface ActivityJsonNodeConverter {

    boolean supportsActivity(final String name);

    Activity toActivity(final JsonNode node);

    static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

    static Instant extractTimestamp(final JsonNode node) {
        return Instant.parse(node.get("timestamp").asText());
    }

}
