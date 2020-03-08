package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.usecases.verification.onetimepasscode.CreateOneTimePasscodeVerificationRequest;

import java.io.IOException;
import java.util.UUID;

public class CreateOneTimePasscodeVerificationRequestDeserializer extends StdDeserializer<CreateOneTimePasscodeVerificationRequest> {

    CreateOneTimePasscodeVerificationRequestDeserializer() {
        super(CreateOneTimePasscodeVerificationRequest.class);
    }

    @Override
    public CreateOneTimePasscodeVerificationRequest deserialize(final JsonParser parser,final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return CreateOneTimePasscodeVerificationRequest.builder()
                .contextId(toUuid(node.get("contextId")))
                .deliveryMethodId(toUuid(node.get("deliveryMethodId")))
                .build();
    }

    private static UUID toUuid(final JsonNode node) {
        return UUID.fromString(node.asText());
    }

}
