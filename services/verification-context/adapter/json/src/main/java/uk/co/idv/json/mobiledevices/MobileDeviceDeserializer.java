package uk.co.idv.json.mobiledevices;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.mobiledevice.MobileDevice;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class MobileDeviceDeserializer extends StdDeserializer<MobileDevice> {

    public MobileDeviceDeserializer() {
        super(MobileDevice.class);
    }

    @Override
    public MobileDevice deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return MobileDevice.builder()
                .id(node.get("id").asText())
                .lastLogin(JsonNodeConverter.toInstant(node.get("lastLogin")))
                .trusted(node.get("trusted").asBoolean())
                .build();
    }

}
