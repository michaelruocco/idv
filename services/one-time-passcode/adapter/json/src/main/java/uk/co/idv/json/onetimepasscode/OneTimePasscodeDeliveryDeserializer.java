package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class OneTimePasscodeDeliveryDeserializer extends StdDeserializer<OneTimePasscodeDelivery> {

    OneTimePasscodeDeliveryDeserializer() {
        super(OneTimePasscodeDelivery.class);
    }

    @Override
    public OneTimePasscodeDelivery deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return OneTimePasscodeDelivery.builder()
                .method(toMethod(parser, node.get("method")))
                .passcode(node.get("passcode").asText())
                .message(node.get("message").asText())
                .sent(JsonNodeConverter.toInstant(node.get("sent")))
                .expiry(JsonNodeConverter.toInstant(node.get("expiry")))
                .build();
    }

    private static DeliveryMethod toMethod(final JsonParser parser, final JsonNode node) {
        return JsonNodeConverter.toObject(node, parser, DeliveryMethod.class);
    }

}
