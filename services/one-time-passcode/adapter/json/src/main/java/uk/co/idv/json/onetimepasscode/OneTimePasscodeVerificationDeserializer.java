package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeDelivery;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerification;
import uk.co.idv.domain.entities.onetimepasscode.OneTimePasscodeVerificationAttempt;
import uk.co.idv.utils.json.converter.jackson.JsonNodeConverter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public class OneTimePasscodeVerificationDeserializer extends StdDeserializer<OneTimePasscodeVerification> {

    public OneTimePasscodeVerificationDeserializer() {
        super(OneTimePasscodeVerification.class);
    }

    @Override
    public OneTimePasscodeVerification deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return OneTimePasscodeVerification.builder()
                .id(JsonNodeConverter.toUUID(node.get("id")))
                .contextId(JsonNodeConverter.toUUID(node.get("contextId")))
                .created(JsonNodeConverter.toInstant(node.get("created")))
                .expiry(JsonNodeConverter.toInstant(node.get("expiry")))
                .status(node.get("status").asText())
                .maxDeliveries(node.get("maxDeliveries").asInt())
                .maxAttempts(node.get("maxAttempts").asInt())
                .deliveries(new ArrayList<>(extractDeliveries(parser, node)))
                .attempts(new ArrayList<>(extractAttempts(parser, node)))
                .completed(extractCompleted(node))
                .build();
    }

    private static Collection<OneTimePasscodeDelivery> extractDeliveries(final JsonParser parser, final JsonNode node) {
        final JsonNode deliveries = node.get("deliveries");
        return Arrays.asList(JsonNodeConverter.toObject(deliveries, parser, OneTimePasscodeDelivery[].class));
    }

    private static Collection<OneTimePasscodeVerificationAttempt> extractAttempts(final JsonParser parser, final JsonNode node) {
        return Optional.ofNullable(node.get("attempts"))
                .map(attempts -> Arrays.asList(JsonNodeConverter.toObject(attempts, parser, OneTimePasscodeVerificationAttempt[].class)))
                .orElse(Collections.emptyList());
    }

    private Instant extractCompleted(final JsonNode node) {
        return Optional.ofNullable(node.get("completed"))
                .map(JsonNodeConverter::toInstant)
                .orElse(null);
    }

}
