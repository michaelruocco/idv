package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.usecases.verification.onetimepasscode.UpdateOneTimePasscodeVerificationRequest;
import uk.co.idv.json.util.JsonNodeConverter;

import java.io.IOException;

public class UpdateOneTimePasscodeVerificationRequestDocumentDeserializer extends StdDeserializer<UpdateOneTimePasscodeVerificationRequestDocument> {

    public UpdateOneTimePasscodeVerificationRequestDocumentDeserializer() {
        super(UpdateOneTimePasscodeVerificationRequestDocument.class);
    }

    @Override
    public UpdateOneTimePasscodeVerificationRequestDocument deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final JsonNode dataNode = node.get("data");
        final JsonNode attributesNode = dataNode.get("attributes");
        final UpdateOneTimePasscodeVerificationRequest request = UpdateOneTimePasscodeVerificationRequest.builder()
                .id(JsonNodeConverter.toUUID(dataNode.get("id")))
                .contextId(JsonNodeConverter.toUUID(attributesNode.get("contextId")))
                .deliveryMethodId(JsonNodeConverter.toUUID(attributesNode.get("deliveryMethodId")))
                .build();
        return new UpdateOneTimePasscodeVerificationRequestDocument(request);
    }

}
