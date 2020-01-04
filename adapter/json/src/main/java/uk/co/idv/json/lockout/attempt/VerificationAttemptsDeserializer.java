package uk.co.idv.json.lockout.attempt;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempt;
import uk.co.idv.domain.entities.lockout.attempt.VerificationAttempts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class VerificationAttemptsDeserializer extends StdDeserializer<VerificationAttempts> {

    public VerificationAttemptsDeserializer() {
        super(Channel.class);
    }

    @Override
    public VerificationAttempts deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return new VerificationAttempts(
                UUID.fromString(node.get("id").asText()),
                UUID.fromString(node.get("idvId").asText()),
                toAttempts(parser, node.get("attempts"))
        );
    }

    private static Collection<VerificationAttempt> toAttempts(final JsonParser parser, final JsonNode node) throws IOException {
        final Collection<VerificationAttempt> attempts = new ArrayList<>();
        for (JsonNode itemNode : node) {
            attempts.add(toAttempt(parser, itemNode));
        }
        return attempts;
    }

    private static VerificationAttempt toAttempt(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(VerificationAttempt.class);
    }

}
