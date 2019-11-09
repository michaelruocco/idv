package uk.co.mruoc.idv.api.lockout;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.identity.Alias;
import uk.co.idv.domain.entities.lockout.DefaultLockoutRequest;
import uk.co.idv.domain.entities.lockout.LockoutRequest;

import java.io.IOException;

public class LockoutRequestDeserializer extends StdDeserializer<LockoutRequest> {

    LockoutRequestDeserializer() {
        super(LockoutRequest.class);
    }

    @Override
    public LockoutRequest deserialize(final JsonParser parser,
                                      final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return DefaultLockoutRequest.builder()
                .channelId(node.get("channelId").asText())
                .activityName(node.get("activityName").asText())
                .alias(toAlias(parser, node.get("alias")))
                .build();
    }

    private static Alias toAlias(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(Alias.class);
    }

}
