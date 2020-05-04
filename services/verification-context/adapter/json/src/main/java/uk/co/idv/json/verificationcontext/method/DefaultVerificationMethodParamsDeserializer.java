package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.params.DefaultVerificationMethodParams;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class DefaultVerificationMethodParamsDeserializer extends StdDeserializer<DefaultVerificationMethodParams> {

    public DefaultVerificationMethodParamsDeserializer() {
        super(DefaultVerificationMethodParams.class);
    }

    @Override
    public DefaultVerificationMethodParams deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultVerificationMethodParams.builder()
                .maxAttempts(node.get("maxAttempts").asInt())
                .duration(JsonNodeConverter.toDuration(node.get("duration")))
                .build();
    }

}
