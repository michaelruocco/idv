package uk.co.mruoc.idv.mongo.dao.channel;

public class FakeChannelDocument extends ChannelDocument {

    public FakeChannelDocument() {
        this("fake-channel");
    }

    public FakeChannelDocument(final String id) {
        setId(id);
    }

}
