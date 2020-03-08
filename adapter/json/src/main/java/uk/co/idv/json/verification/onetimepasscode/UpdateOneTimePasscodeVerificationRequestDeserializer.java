package uk.co.idv.json.verification.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequest;

import java.io.IOException;
import java.util.UUID;

public class UpdateOneTimePasscodeVerificationRequestDeserializer extends StdDeserializer<UpdateOneTimePasscodeVerificationRequest> {

    UpdateOneTimePasscodeVerificationRequestDeserializer() {
        super(UpdateOneTimePasscodeVerificationRequest.class);
    }

    @Override
    public UpdateOneTimePasscodeVerificationRequest deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return UpdateOneTimePasscodeVerificationRequest.builder()
                .id(toUuid(node.get("id")))
                .contextId(toUuid(node.get("contextId")))
                .deliveryMethodId(toUuid(node.get("deliveryMethodId")))
                .build();
    }

    private static UUID toUuid(final JsonNode node) {
        return UUID.fromString(node.asText());
    }

}
