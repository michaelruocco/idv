package uk.co.idv.json.channel;

import com.fasterxml.jackson.databind.JsonNode;

public class ChannelIdExtractor {

    public static String extractId(final JsonNode node) {
        return node.get("id").asText();
    }

}
