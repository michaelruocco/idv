package uk.co.idv.json.activity;

import com.fasterxml.jackson.databind.JsonNode;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.SimpleActivity;


public class JsonNodeToSimpleActivityConverter implements JsonNodeToActivityConverter {

    public Activity toActivity(final JsonNode node) {
        return SimpleActivity.builder()
                .name(extractName(node))
                .timestamp(JsonNodeToActivityConverter.extractTimestamp(node))
                .build();
    }

    private static String extractName(final JsonNode node) {
        return node.get("name").asText();
    }

}
