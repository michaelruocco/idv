package uk.co.idv.json.channel.simple;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.SimpleChannel;
import uk.co.idv.json.channel.ChannelIdExtractor;
import uk.co.idv.json.channel.ChannelJsonNodeConverter;

@Slf4j
public class AllSimpleChannelJsonNodeConverter implements ChannelJsonNodeConverter {

    @Override
    public boolean supportsChannel(final String id) {
        log.info("all channels ids supported");
        return true;
    }

    @Override
    public Channel toChannel(final JsonNode node) {
        final String id = ChannelIdExtractor.extractId(node);
        log.info("converting {} to simple channel", id);
        return new SimpleChannel(id);
    }

}
