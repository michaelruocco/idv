package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.usecases.onetimepasscode.send.SendOneTimePasscodeRequest;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.io.IOException;

public class SendOneTimePasscodeRequestDeserializer extends StdDeserializer<SendOneTimePasscodeRequest> {

    SendOneTimePasscodeRequestDeserializer() {
        super(SendOneTimePasscodeRequest.class);
    }

    @Override
    public SendOneTimePasscodeRequest deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return SendOneTimePasscodeRequest.builder()
                .contextId(JsonNodeConverter.toUUID(node.get("contextId")))
                .deliveryMethodId(JsonNodeConverter.toUUID(node.get("deliveryMethodId")))
                .build();
    }

}
