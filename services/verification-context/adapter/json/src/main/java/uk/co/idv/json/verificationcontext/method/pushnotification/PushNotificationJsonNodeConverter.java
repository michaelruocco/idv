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
import uk.co.idv.json.verificationcontext.method.AbstractVerificationMethodJsonNodeConverter;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.extractEligible;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.extractResults;

@Slf4j
public class PushNotificationJsonNodeConverter extends AbstractVerificationMethodJsonNodeConverter {

    public PushNotificationJsonNodeConverter() {
        super(PushNotification.NAME);
    }

    @Override
    public VerificationMethod toMethod(final JsonNode node,
                                       final JsonParser parser,
                                       final DeserializationContext context) {
        final boolean eligible = extractEligible(node);
        if (eligible) {
            final VerificationResults results = extractResults(node, parser);
            return new PushNotificationEligible(results);
        }
        return new PushNotificationIneligible();
    }

}
