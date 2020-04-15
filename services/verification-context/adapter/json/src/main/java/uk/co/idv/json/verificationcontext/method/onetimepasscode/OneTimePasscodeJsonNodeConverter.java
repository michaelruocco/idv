package uk.co.idv.json.verificationcontext.method.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.DeliveryMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeEligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscodeIneligible;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.PasscodeSettings;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.json.verificationcontext.method.AbstractVerificationMethodJsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.util.Arrays;
import java.util.Collection;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.extractEligible;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.extractResults;

@Slf4j
public class OneTimePasscodeJsonNodeConverter extends AbstractVerificationMethodJsonNodeConverter {

    public OneTimePasscodeJsonNodeConverter() {
        super(OneTimePasscode.NAME);
    }

    @Override
    public VerificationMethod toMethod(final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) {
        final boolean eligible = extractEligible(node);
        if (eligible) {
            final VerificationResults results = extractResults(node, parser);
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
