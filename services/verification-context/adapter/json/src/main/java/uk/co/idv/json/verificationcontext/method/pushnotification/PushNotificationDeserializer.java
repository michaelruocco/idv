package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.eligibility.NoMobileApplication;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
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
            return PushNotification.eligibleBuilder()
                    .params(toParams(node, parser))
                    .results(toResults(node, parser))
                    .build();
        }
        //TODO add ineligible deserializer to pick up reason on fly
        return PushNotification.ineligible(new NoMobileApplication());
    }

    public static VerificationMethodParams toParams(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("parameters"), parser, DefaultVerificationMethodParams.class);
    }

}
