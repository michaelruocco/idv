package uk.co.idv.json.channel;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.usecases.exception.ChannelNotSupportedException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public class ChannelDeserializer extends StdDeserializer<Channel> {

    private final Collection<ChannelJsonNodeConverter> converters;

    public ChannelDeserializer(final ChannelJsonNodeConverter... converters) {
        this(Arrays.asList(converters));
    }

    public ChannelDeserializer(final Collection<ChannelJsonNodeConverter> converters) {
        super(Channel.class);
        this.converters = converters;
    }

    @Override
    public Channel deserialize(final JsonParser parser, final DeserializationContext context) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final String id = ChannelJsonNodeConverter.extractId(node);
        return findConverter(id)
                .map(converter -> converter.toChannel(node))
                .orElseThrow(() -> new ChannelNotSupportedException(id));
    }

    private Optional<ChannelJsonNodeConverter> findConverter(final String id) {
        return converters.stream().filter(converter -> converter.supportsChannel(id)).findFirst();
    }

}
