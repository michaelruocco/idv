package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.policy.OneTimePasscodePolicy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class OneTimePasscodePolicyDeserializer extends StdDeserializer<OneTimePasscodePolicy> {

    public OneTimePasscodePolicyDeserializer() {
        super(OneTimePasscodePolicy.class);
    }

    @Override
    public OneTimePasscodePolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new OneTimePasscodePolicy(toParams(node, parser));
    }

    private static OneTimePasscodeParams toParams(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("parameters"), parser, OneTimePasscodeParams.class);
    }

}
