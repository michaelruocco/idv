package uk.co.idv.repository.mongo.channel;

import uk.co.idv.domain.entities.channel.Channel;

public interface ChannelDocumentConverter {

    boolean supportsChannel(String id);

    ChannelDocument toDocument(Channel channel);

    Channel toChannel(ChannelDocument document);

}
