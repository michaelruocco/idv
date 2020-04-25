package uk.co.idv.json.identity.alias.idvid;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.identity.alias.IdvId;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class IdvIdDeserializer extends StdDeserializer<IdvId> {

    public IdvIdDeserializer() {
        super(IdvId.class);
    }

    @Override
    public IdvId deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new IdvId(JsonNodeConverter.toUUID(node.get("value")));
    }

}
