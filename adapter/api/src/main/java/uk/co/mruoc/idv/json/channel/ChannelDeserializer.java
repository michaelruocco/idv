package uk.co.mruoc.idv.json.channel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.usecases.exception.ChannelNotSupportedException;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.Rsa;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChannelDeserializer extends StdDeserializer<Channel> {

    private final Map<String, JsonNodeToChannelConverter> converters = buildConverters();

    public ChannelDeserializer() {
        super(Channel.class);
    }

    @Override
    public Channel deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String id = extractId(node);
        if (converters.containsKey(id)) {
            final JsonNodeToChannelConverter converter = converters.get(id);
            return converter.toChannel(node);
        }
        throw new ChannelNotSupportedException(id);
    }

    private static String extractId(final JsonNode node) {
        return node.get("id").asText();
    }

    private static Map<String, JsonNodeToChannelConverter> buildConverters() {
        final Map<String, JsonNodeToChannelConverter> converters = new HashMap<>();
        converters.put(Rsa.ID, new JsonNodeToRsaConverter());
        return Collections.unmodifiableMap(converters);
    }

}
