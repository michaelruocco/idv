package uk.co.idv.json.verificationcontext.method.pushnotification;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.params.DefaultVerificationMethodParams;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.policy.PushNotificationPolicy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

public class PushNotificationPolicyDeserializer extends StdDeserializer<PushNotificationPolicy> {

    public PushNotificationPolicyDeserializer() {
        super(PushNotificationPolicy.class);
    }

    @Override
    public PushNotificationPolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        return new PushNotificationPolicy(toParams(node, parser));
    }

    private static VerificationMethodParams toParams(final JsonNode node, final JsonParser parser) {
        return JsonNodeConverter.toObject(node.get("parameters"), parser, DefaultVerificationMethodParams.class);
    }

}
