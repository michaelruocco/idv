package uk.co.idv.repository.mongo.channel.simple;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.SimpleChannel;
import uk.co.idv.repository.mongo.channel.ChannelDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverter;

@Slf4j
public class AllSimpleChannelDocumentConverter implements ChannelDocumentConverter {

    @Override
    public boolean supportsChannel(String id) {
        log.info("all channels ids supported");
        return true;
    }

    public ChannelDocument toDocument(final Channel channel) {
        final ChannelDocument document = new ChannelDocument();
        document.setId(channel.getId());
        return document;
    }

    public Channel toChannel(final ChannelDocument document) {
        final String id = document.getId();
        return new SimpleChannel(id);
    }

}
