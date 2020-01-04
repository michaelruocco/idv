package uk.co.idv.json.lockout.attempt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.lockout.attempt.DefaultVerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class VerificationAttemptDeserializer extends StdDeserializer<VerificationAttempt> {

    public VerificationAttemptDeserializer() {
        super(Channel.class);
    }

    @Override
    public VerificationAttempt deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return DefaultVerificationAttempt.builder()
                .contextId(UUID.fromString(node.get("contextId").asText()))
                .channelId(node.get("channelId").asText())
                .activityName(node.get("activityName").asText())
                .methodName(node.get("methodName").asText())
                .alias(node.get("alias").traverse(parser.getCodec()).readValueAs(Alias.class))
                .idvIdValue(UUID.fromString(node.get("idvId").asText()))
                .verificationId(UUID.fromString(node.get("verificationId").asText()))
                .timestamp(Instant.parse(node.get("timestamp").asText()))
                .successful(node.get("successful").asBoolean())
                .build();
    }

}
