package uk.co.idv.json.activity;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.activity.Activity;

import java.time.Instant;

public interface JsonNodeToActivityConverter {

    Activity toActivity(final JsonNode node);

    static Instant extractTimestamp(final JsonNode node) {
        return Instant.parse(node.get("timestamp").asText());
    }

}
