package uk.co.idv.json.verificationcontext.method.pinsentry.mobile;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.policy.MobilePinsentryPolicy;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class MobilePinsentryPolicyDeserializer extends StdDeserializer<MobilePinsentryPolicy> {

    public MobilePinsentryPolicyDeserializer() {
        super(MobilePinsentryPolicy.class);
    }

    @Override
    public MobilePinsentryPolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new MobilePinsentryPolicy(toParams(node, parser));
    }

    private static PinsentryParams toParams(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("parameters"), parser, PinsentryParams.class);
    }

}
