package uk.co.idv.repository.mongo.channel;

import uk.co.idv.domain.usecases.exception.ChannelNotSupportedException;
import uk.co.idv.domain.entities.channel.Channel;
import uk.co.idv.domain.entities.channel.Rsa;

public class ChannelDocumentConverter {

    public Channel toChannel(final ChannelDocument document) {
        final String id = document.getId();
        if (Rsa.ID.equals(id)) {
            return new Rsa();
        }
        throw new ChannelNotSupportedException(id);
    }

    public ChannelDocument toDocument(final Channel channel) {
        final ChannelDocument document = new ChannelDocument();
        document.setId(channel.getId());
        return document;
    }

}
