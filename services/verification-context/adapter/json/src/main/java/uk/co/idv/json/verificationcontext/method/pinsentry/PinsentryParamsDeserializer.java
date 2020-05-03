package uk.co.idv.json.verificationcontext.method.pinsentry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.PinsentryParams;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class PinsentryParamsDeserializer extends StdDeserializer<PinsentryParams> {

    public PinsentryParamsDeserializer() {
        super(PinsentryParams.class);
    }

    @Override
    public PinsentryParams deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return PinsentryParams.builder()
                .maxAttempts(node.get("maxAttempts").asInt())
                .duration(JsonNodeConverter.toDuration(node.get("duration")))
                .function(PinsentryFunctionJsonNodeConverter.extractFunction(node))
                .build();
    }

}
