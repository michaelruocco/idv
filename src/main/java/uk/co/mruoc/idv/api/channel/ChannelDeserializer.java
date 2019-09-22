package uk.co.mruoc.idv.api.channel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.Rsa;

import java.io.IOException;

import static uk.co.mruoc.idv.api.channel.JsonNodeToRsaConverter.toRsa;

public class ChannelDeserializer extends StdDeserializer<Channel> {

    public ChannelDeserializer() {
        super(Channel.class);
    }

    @Override
    public Channel deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String id = extractId(node);
        if (Rsa.ID.equals(id)) {
            return toRsa();
        }
        throw new ChannelNotSupportedException(id);
    }

    private static String extractId(final JsonNode node) {
        return node.get("id").asText();
    }

    public static class ChannelNotSupportedException extends RuntimeException {

        private ChannelNotSupportedException(final String channelId) {
            super(channelId);
        }

    }

}
