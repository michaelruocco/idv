package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.UUID;

public abstract class AbstractJsonApiDocumentWithIdDeserializer<T extends JsonApiDocumentWithId> extends StdDeserializer<T> {

    protected AbstractJsonApiDocumentWithIdDeserializer(final Class<T> type) {
        super(type);
    }

    @Override
    public T deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final JsonNode data = JsonNodeExtractor.extractDataNode(node);
        final String jsonApiType = JsonNodeExtractor.extractJsonApiType(data);
        final UUID id = extractId(data);
        final JsonNode attributes = JsonNodeExtractor.extractAttributesNode(data);
        return toDocument(parser, attributes, jsonApiType, id);
    }

    private static UUID extractId(final JsonNode node) {
        return UUID.fromString(node.get("id").asText());
    }

    protected abstract T toDocument(final JsonParser parser,
                                    final JsonNode attributesNode,
                                    final String jsonApiType,
                                    final UUID id) throws IOException;

}
