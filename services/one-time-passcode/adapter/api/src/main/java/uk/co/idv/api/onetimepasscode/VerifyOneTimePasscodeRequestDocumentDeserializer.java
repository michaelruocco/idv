package uk.co.idv.api.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequest;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.io.IOException;

public class VerifyOneTimePasscodeRequestDocumentDeserializer extends StdDeserializer<VerifyOneTimePasscodeRequestDocument> {

    public VerifyOneTimePasscodeRequestDocumentDeserializer() {
        super(VerifyOneTimePasscodeRequestDocument.class);
    }

    @Override
    public VerifyOneTimePasscodeRequestDocument deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final JsonNode dataNode = node.get("data");
        final JsonNode attributesNode = dataNode.get("attributes");
        final VerifyOneTimePasscodeRequest request = VerifyOneTimePasscodeRequest.builder()
                .id(JsonNodeConverter.toUUID(dataNode.get("id")))
                .passcodes(JsonNodeConverter.toStringCollection(attributesNode.get("passcodes"), parser))
                .build();
        return new VerifyOneTimePasscodeRequestDocument(request);
    }

}
