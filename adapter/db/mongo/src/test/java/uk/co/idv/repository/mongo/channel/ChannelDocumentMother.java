package uk.co.idv.repository.mongo.channel;

public class ChannelDocumentMother  {

    public static ChannelDocument fake() {
        return build("fake-channel");
    }

    public static ChannelDocument notSupported() {
        return build("not-supported");
    }

    public static ChannelDocument build(final String id) {
        final ChannelDocument document = new ChannelDocument();
        document.setId(id);
        return document;
    }

}
