package uk.co.mruoc.idv.mongo.beantest;

import org.meanbean.lang.Factory;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelDocument;
import uk.co.mruoc.idv.mongo.dao.channel.ChannelDocumentMother;

public class ChannelDocumentFactory implements Factory<ChannelDocument> {

    @Override
    public ChannelDocument create() {
        return ChannelDocumentMother.fake();
    }

}
