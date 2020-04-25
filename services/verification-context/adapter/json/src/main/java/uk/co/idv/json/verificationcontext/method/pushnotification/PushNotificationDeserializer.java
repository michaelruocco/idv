package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationEligible;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotificationIneligible;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.isEligible;
import static uk.co.idv.json.verificationcontext.method.VerificationMethodJsonNodeConverter.toResults;

public class PushNotificationDeserializer extends StdDeserializer<PushNotification> {

    public PushNotificationDeserializer() {
        super(PushNotification.class);
    }

    @Override
    public PushNotification deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        if (isEligible(node)) {
            return new PushNotificationEligible(toResults(node, parser));
        }
        return new PushNotificationIneligible();
    }

}
