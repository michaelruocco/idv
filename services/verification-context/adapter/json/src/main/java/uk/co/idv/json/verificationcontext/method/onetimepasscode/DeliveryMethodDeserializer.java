package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.SmsDeliveryMethod;

import java.io.IOException;
import java.util.UUID;

public class DeliveryMethodDeserializer extends StdDeserializer<DeliveryMethod> {

    public DeliveryMethodDeserializer() {
        super(DeliveryMethod.class);
    }

    @Override
    public SmsDeliveryMethod deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return new SmsDeliveryMethod(
                UUID.fromString(node.get("id").asText()),
                node.get("value").asText()
        );
    }

}
