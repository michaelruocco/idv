package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.usecases.onetimepasscode.verify.VerifyOneTimePasscodeRequest;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.io.IOException;

public class VerifyOneTimePasscodeRequestDeserializer extends StdDeserializer<VerifyOneTimePasscodeRequest> {

    VerifyOneTimePasscodeRequestDeserializer() {
        super(VerifyOneTimePasscodeRequest.class);
    }

    @Override
    public VerifyOneTimePasscodeRequest deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return VerifyOneTimePasscodeRequest.builder()
                .id(JsonNodeConverter.toUUID(node.get("id")))
                .passcodes(JsonNodeConverter.toStringCollection(node.get("passcodes"), parser))
                .build();
    }

}
