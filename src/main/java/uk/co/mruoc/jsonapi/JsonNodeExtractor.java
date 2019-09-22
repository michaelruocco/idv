package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonNodeExtractor {

    public static JsonNode extractDataNode(final JsonNode node) {
        return node.get("data");
    }

    public static String extractJsonApiType(final JsonNode node) {
        return node.get("type").asText();
    }

    public static JsonNode extractAttributesNode(final JsonNode node) {
        return node.get("attributes");
    }

}
