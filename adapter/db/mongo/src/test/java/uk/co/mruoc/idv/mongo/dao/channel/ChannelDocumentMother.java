package uk.co.mruoc.idv.mongo.dao.channel;

public class ChannelDocumentMother  {

    public static ChannelDocument fake() {
        return build("fake-channel");
    }

    public static ChannelDocument rsa() {
        return build("RSA");
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
