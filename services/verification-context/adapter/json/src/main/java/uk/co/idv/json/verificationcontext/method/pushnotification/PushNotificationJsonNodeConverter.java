package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.domain.entities.verificationcontext.result.VerificationResults;
import uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter;

import java.io.IOException;

@Slf4j
public class PushNotificationJsonNodeConverter implements VerificationMethodJsonNodeConverter {

    @Override
    public boolean supportsMethod(final String name) {
        boolean supported = PushNotification.NAME.equals(name);
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
            return new PushNotificationEligible(results);
        }
        return new PushNotificationIneligible();
    }

}