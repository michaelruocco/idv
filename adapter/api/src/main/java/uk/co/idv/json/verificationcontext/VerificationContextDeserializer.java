package uk.co.idv.json.verificationcontext;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.identity.Identity;
import uk.co.idv.domain.entities.identity.alias.Alias;
import uk.co.idv.domain.entities.verificationcontext.VerificationContext;
import uk.co.idv.domain.entities.verificationcontext.VerificationSequences;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

public class VerificationContextDeserializer extends StdDeserializer<VerificationContext> {

    protected VerificationContextDeserializer() {
        super(VerificationContext.class);
    }

    @Override
    public VerificationContext deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return VerificationContext.builder()
                .id(UUID.fromString(node.get("id").asText()))
                .channel(node.get("channel").traverse(parser.getCodec()).readValueAs(Channel.class))
                .providedAlias(node.get("providedAlias").traverse(parser.getCodec()).readValueAs(Alias.class))
                .identity(node.get("identity").traverse(parser.getCodec()).readValueAs(Identity.class))
                .activity(node.get("activity").traverse(parser.getCodec()).readValueAs(Activity.class))
                .created(Instant.parse(node.get("created").asText()))
                .expiry(Instant.parse(node.get("expiry").asText()))
                .sequences(node.get("sequences").traverse(parser.getCodec()).readValueAs(VerificationSequences.class))
                .build();
    }

}
