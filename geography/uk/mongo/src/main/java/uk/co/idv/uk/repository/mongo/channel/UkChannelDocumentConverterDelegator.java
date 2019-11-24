package uk.co.idv.uk.repository.mongo.channel;

import uk.co.idv.repository.mongo.channel.ChannelDocumentConverterDelegator;
import uk.co.idv.repository.mongo.channel.simple.AllSimpleChannelDocumentConverter;
import uk.co.idv.uk.repository.mongo.channel.rsa.RsaChannelDocumentConverter;

public class UkChannelDocumentConverterDelegator extends ChannelDocumentConverterDelegator {

    public UkChannelDocumentConverterDelegator() {
        super(
                new RsaChannelDocumentConverter(),
                new AllSimpleChannelDocumentConverter()
        );
    }

}
