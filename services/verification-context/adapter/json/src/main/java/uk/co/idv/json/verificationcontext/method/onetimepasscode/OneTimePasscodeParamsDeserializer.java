package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.DefaultOneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.PasscodeSettings;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class OneTimePasscodeParamsDeserializer extends StdDeserializer<OneTimePasscodeParams> {

    public OneTimePasscodeParamsDeserializer() {
        super(OneTimePasscodeParams.class);
    }

    @Override
    public OneTimePasscodeParams deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return DefaultOneTimePasscodeParams.builder()
                .maxAttempts(node.get("maxAttempts").asInt())
                .duration(JsonNodeConverter.toDuration(node.get("duration")))
                .passcodeSettings(JsonNodeConverter.toObject(node.get("passcodeSettings"), parser, PasscodeSettings.class))
                .build();
    }

}
