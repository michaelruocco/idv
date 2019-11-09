package uk.co.mruoc.idv.mongo.dao.channel;

import uk.co.mruoc.idv.domain.exception.ChannelNotSupportedException;
import uk.co.mruoc.idv.domain.model.channel.Channel;
import uk.co.mruoc.idv.domain.model.channel.Rsa;

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
