package uk.co.idv.repository.mongo.channel.simple;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.SimpleChannel;
import uk.co.idv.repository.mongo.channel.ChannelDocument;
import uk.co.idv.repository.mongo.channel.ChannelDocumentConverter;

@Slf4j
public class SingleSimpleChannelDocumentConverter implements ChannelDocumentConverter {

    private final String supportedId;

    public SingleSimpleChannelDocumentConverter(final String supportedId) {
        this.supportedId = supportedId;
    }

    @Override
    public boolean supportsChannel(String id) {
        boolean supported = this.supportedId.equals(id);
        log.info("returning supported {} for channel id {}", supported, id);
        return supported;
    }

    public ChannelDocument toDocument(final Channel channel) {
        final ChannelDocument document = new ChannelDocument();
        document.setId(channel.getId());
        return document;
    }

    public Channel toChannel(final ChannelDocument document) {
        return new SimpleChannel(document.getId());
    }

}
