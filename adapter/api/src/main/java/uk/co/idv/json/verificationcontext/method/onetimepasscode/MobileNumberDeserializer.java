package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.MobileNumber;

import java.io.IOException;
import java.util.UUID;

public class MobileNumberDeserializer extends StdDeserializer<MobileNumber> {

    public MobileNumberDeserializer() {
        super(MobileNumber.class);
    }

    @Override
    public MobileNumber deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return new MobileNumber(
                UUID.fromString(node.get("id").asText()),
                node.get("number").asText()
        );
    }

}
