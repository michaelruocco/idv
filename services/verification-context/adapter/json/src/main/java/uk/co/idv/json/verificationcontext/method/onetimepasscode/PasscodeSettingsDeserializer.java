package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultPasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeParams;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class PasscodeSettingsDeserializer extends StdDeserializer<PasscodeParams> {

    public PasscodeSettingsDeserializer() {
        super(PasscodeParams.class);
    }

    @Override
    public PasscodeParams deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultPasscodeParams.builder()
                .length(node.get("length").asInt())
                .duration(JsonNodeConverter.toDuration(node.get("duration")))
                .maxDeliveries(node.get("maxDeliveries").asInt())
                .build();
    }

}
