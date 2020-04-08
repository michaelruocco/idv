package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;

import java.io.IOException;
import java.time.Instant;

public class OneTimePasscodeDeliveryDeserializer extends StdDeserializer<OneTimePasscodeDelivery> {

    OneTimePasscodeDeliveryDeserializer() {
        super(OneTimePasscodeDelivery.class);
    }

    @Override
    public OneTimePasscodeDelivery deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return OneTimePasscodeDelivery.builder()
                .method(toMethod(parser, node.get("method")))
                .passcode(node.get("passcode").asText())
                .message(node.get("message").asText())
                .sent(Instant.parse(node.get("sent").asText()))
                .build();
    }

    private static DeliveryMethod toMethod(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(DeliveryMethod.class);
    }

}
