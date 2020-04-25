package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Arrays;
import java.util.Collection;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.isEligible;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toResults;

public class OneTimePasscodeDeserializer extends StdDeserializer<OneTimePasscode> {

    public OneTimePasscodeDeserializer() {
        super(OneTimePasscode.class);
    }

    @Override
    public OneTimePasscode deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        if (isEligible(node)) {
            final VerificationResults results = toResults(node, parser);
            final PasscodeSettings settings = extractPasscodeSettings(node, parser);
            final Collection<DeliveryMethod> deliveryMethods = extractDeliveryMethods(node, parser);
            return new OneTimePasscodeEligible(settings, deliveryMethods, results);
        }
        return new OneTimePasscodeIneligible();
    }

    private static PasscodeSettings extractPasscodeSettings(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("passcodeSettings"), parser, PasscodeSettings.class);
    }

    private static Collection<DeliveryMethod> extractDeliveryMethods(final JsonNode node, final JsonParser parser) {
        final DeliveryMethod[] deliveryMethods = JsonNodeConverter.toObject(node.get("deliveryMethods"), parser, DeliveryMethod[].class);
        return Arrays.asList(deliveryMethods);
    }

}
