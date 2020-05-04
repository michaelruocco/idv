package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultPasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeSettings;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class PasscodeSettingsDeserializer extends StdDeserializer<PasscodeSettings> {

    public PasscodeSettingsDeserializer() {
        super(PasscodeSettings.class);
    }

    @Override
    public PasscodeSettings deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultPasscodeSettings.builder()
                .length(node.get("length").asInt())
                .duration(JsonNodeConverter.toDuration(node.get("duration")))
                .maxDeliveries(node.get("maxDeliveries").asInt())
                .build();
    }

}
