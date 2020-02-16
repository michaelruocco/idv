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
import uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

@Slf4j
public class OneTimePasscodeJsonNodeConverter implements VerificationMethodJsonNodeConverter {

    @Override
    public boolean supportsMethod(final String name) {
        boolean supported = OneTimePasscode.NAME.equals(name);
        log.info("returning supported {} for method name {}", supported, name);
        return supported;
    }

    @Override
    public VerificationMethod toMethod(final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) throws IOException {
        final boolean eligible = node.get("eligible").asBoolean();
        if (eligible) {
            final VerificationResults results = node.get("results").traverse(parser.getCodec()).readValueAs(VerificationResults.class);
            final PasscodeSettings settings = node.get("passcodeSettings").traverse(parser.getCodec()).readValueAs(PasscodeSettings.class);
            final Collection<DeliveryMethod> deliveryMethods = Arrays.asList(node.get("deliveryMethods").traverse(parser.getCodec()).readValueAs(DeliveryMethod[].class));
            return new OneTimePasscodeEligible(settings, deliveryMethods, results);
        }
        return new OneTimePasscodeIneligible();
    }

}
