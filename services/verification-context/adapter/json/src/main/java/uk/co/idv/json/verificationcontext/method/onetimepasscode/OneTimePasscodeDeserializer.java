package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.params.OneTimePasscodeParams;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toEligibility;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toResults;

public class OneTimePasscodeDeserializer extends StdDeserializer<OneTimePasscode> {

    public OneTimePasscodeDeserializer() {
        super(OneTimePasscode.class);
    }

    @Override
    public OneTimePasscode deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new OneTimePasscode(
                extractParams(node, parser),
                toEligibility(node, parser),
                toResults(node, parser),
                extractDeliveryMethods(node, parser)
        );
    }

    private static OneTimePasscodeParams extractParams(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("parameters"), parser, OneTimePasscodeParams.class);
    }

    private static Collection<DeliveryMethod> extractDeliveryMethods(final JsonNode node, final JsonParser parser) {
        return Optional.ofNullable(node.get("deliveryMethods"))
                .map(results -> Arrays.asList(JsonNodeConverter.toObject(results, parser, DeliveryMethod[].class)))
                .orElseGet(Collections::emptyList);
    }

}
