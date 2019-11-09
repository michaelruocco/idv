package uk.co.idv.repository.mongo.channel;

import org.meanbean.lang.Factory;

public class ChannelDocumentFactory implements Factory<ChannelDocument> {

    @Override
    public ChannelDocument create() {
        return ChannelDocumentMother.fake();
    }

}
