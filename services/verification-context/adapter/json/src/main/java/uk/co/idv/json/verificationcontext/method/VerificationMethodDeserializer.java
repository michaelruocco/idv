package uk.co.idv.json.verificationcontext.method;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.verificationcontext.method.VerificationMethod;
import uk.co.idv.domain.entities.verificationcontext.method.onetimepasscode.OneTimePasscode;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.mobile.MobilePinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pinsentry.physical.PhysicalPinsentry;
import uk.co.idv.domain.entities.verificationcontext.method.pushnotification.PushNotification;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;
import uk.co.idv.utils.json.converter.jackson.JsonParserConverter;

import java.util.Map;

public class VerificationMethodDeserializer extends StdDeserializer<VerificationMethod> {

    private final Map<String, Class<? extends VerificationMethod>> mappings;

    public VerificationMethodDeserializer() {
        this(buildDefaultMappings());
    }

    public VerificationMethodDeserializer(final Map<String, Class<? extends VerificationMethod>> mappings) {
        super(VerificationMethod.class);
        this.mappings = mappings;
    }

    @Override
    public VerificationMethod deserialize(final JsonParser parser, final DeserializationContext context) {
        final JsonNode node = JsonParserConverter.toNode(parser);
        final String name = node.get("name").asText();
        return JsonNodeConverter.toObject(node, parser, mappings.get(name));
    }

    private static Map<String, Class<? extends VerificationMethod>> buildDefaultMappings() {
        return Map.of(
                PushNotification.NAME, PushNotification.class,
                OneTimePasscode.NAME, OneTimePasscode.class,
                PhysicalPinsentry.NAME, PhysicalPinsentry.class,
                MobilePinsentry.NAME, MobilePinsentry.class
        );
    }

}
