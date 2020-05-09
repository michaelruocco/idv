package uk.co.idv.json.verificationcontext.method.pinsentry.physical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.params.PinsentryParams;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.policy.PhysicalPinsentryPolicy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class PhysicalPinsentryPolicyDeserializer extends StdDeserializer<PhysicalPinsentryPolicy> {

    public PhysicalPinsentryPolicyDeserializer() {
        super(PhysicalPinsentryPolicy.class);
    }

    @Override
    public PhysicalPinsentryPolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new PhysicalPinsentryPolicy(toParams(node, parser));
    }

    private static PinsentryParams toParams(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("parameters"), parser, PinsentryParams.class);
    }

}
