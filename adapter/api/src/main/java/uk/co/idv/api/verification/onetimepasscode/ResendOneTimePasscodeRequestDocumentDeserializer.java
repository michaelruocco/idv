package uk.co.idv.api.verification.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.usecases.onetimepasscode.ResendOneTimePasscodeRequest;
import uk.co.idv.json.util.JsonNodeConverter;

import java.io.IOException;

public class ResendOneTimePasscodeRequestDocumentDeserializer extends StdDeserializer<ResendOneTimePasscodeRequestDocument> {

    public ResendOneTimePasscodeRequestDocumentDeserializer() {
        super(ResendOneTimePasscodeRequestDocument.class);
    }

    @Override
    public ResendOneTimePasscodeRequestDocument deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final JsonNode dataNode = node.get("data");
        final JsonNode attributesNode = dataNode.get("attributes");
        final ResendOneTimePasscodeRequest request = ResendOneTimePasscodeRequest.builder()
                .id(JsonNodeConverter.toUUID(dataNode.get("id")))
                .contextId(JsonNodeConverter.toUUID(attributesNode.get("contextId")))
                .deliveryMethodId(JsonNodeConverter.toUUID(attributesNode.get("deliveryMethodId")))
                .build();
        return new ResendOneTimePasscodeRequestDocument(request);
    }

}
