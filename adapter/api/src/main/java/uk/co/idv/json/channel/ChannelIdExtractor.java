package uk.co.idv.json.channel;

import com.fasterxml.jackson.databind.JsonNode;

public class ChannelIdExtractor {

    private ChannelIdExtractor() {
        // utility class
    }

    public static String extractId(final JsonNode node) {
        return node.get("id").asText();
    }

}
