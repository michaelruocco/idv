package uk.co.mruoc.idv.lockout.jsonapi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.identity.domain.model.Alias;

import java.io.IOException;

public class ResetLockoutStateAttributesDeserializer extends StdDeserializer<ResetLockoutStateAttributes> {

    ResetLockoutStateAttributesDeserializer() {
        super(ResetLockoutStateAttributes.class);
    }

    @Override
    public ResetLockoutStateAttributes deserialize(final JsonParser parser,final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return ResetLockoutStateAttributes.builder()
                .channelId(node.get("channelId").asText())
                .activityName(node.get("activityName").asText())
                .alias(toAlias(parser, node.get("alias")))
                .build();
    }

    private static Alias toAlias(final JsonParser parser, final JsonNode node) throws IOException {
        return node.traverse(parser.getCodec()).readValueAs(Alias.class);
    }

}
