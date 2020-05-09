package uk.co.idv.json.verificationcontext.policy;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethodPolicy;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.policy.OneTimePasscodePolicy;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.policy.MobilePinsentryPolicy;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.policy.PhysicalPinsentryPolicy;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.policy.PushNotificationPolicy;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Map;

public class VerificationMethodPolicyDeserializer extends StdDeserializer<VerificationMethodPolicy> {

    private final Map<String, Class<? extends VerificationMethodPolicy>> mappings;

    public VerificationMethodPolicyDeserializer() {
        this(buildDefaultMappings());
    }

    public VerificationMethodPolicyDeserializer(final Map<String, Class<? extends VerificationMethodPolicy>> mappings) {
        super(VerificationMethodPolicy.class);
        this.mappings = mappings;
    }

    @Override
    public VerificationMethodPolicy deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String name = node.get("name").asText();
        return JsonNodeConverter.toObject(node, parser, mappings.get(name));
    }

    private static Map<String, Class<? extends VerificationMethodPolicy>> buildDefaultMappings() {
        return Map.of(
                PushNotification.NAME, PushNotificationPolicy.class,
                OneTimePasscode.NAME, OneTimePasscodePolicy.class,
                PhysicalPinsentry.NAME, PhysicalPinsentryPolicy.class,
                MobilePinsentry.NAME, MobilePinsentryPolicy.class
        );
    }

}
