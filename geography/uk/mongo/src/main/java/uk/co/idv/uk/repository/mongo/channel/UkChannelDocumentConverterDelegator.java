package uk.co.idv.uk.repository.mongo.channel;

import uk.co.idv.repository.mongo.channel.ChannelDocumentConverterDelegator;

public class UkChannelDocumentConverterDelegator extends ChannelDocumentConverterDelegator {

    public UkChannelDocumentConverterDelegator() {
        super(new RsaChannelDocumentConverter());
    }

}
