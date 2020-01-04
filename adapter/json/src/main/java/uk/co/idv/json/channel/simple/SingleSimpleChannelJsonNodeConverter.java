package uk.co.idv.json.channel.simple;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.SimpleChannel;
import uk.co.idv.json.channel.ChannelJsonNodeConverter;

@Slf4j
@RequiredArgsConstructor
public class SingleSimpleChannelJsonNodeConverter implements ChannelJsonNodeConverter {

    private final String supportedId;

    @Override
    public boolean supportsChannel(final String id) {
        boolean supported = this.supportedId.equals(id);
        log.info("returning supported {} for channel id {}", supported, id);
        return supported;
    }

    @Override
    public Channel toChannel(final JsonNode node) {
        final String id = ChannelJsonNodeConverter.extractId(node);
        log.info("converting {} to simple channel", id);
        return new SimpleChannel(id);
    }

}
