package uk.co.mruoc.idv.mongo.dao.activity;

public class FakeActivityDocument extends ActivityDocument {

    public FakeActivityDocument(final String timestamp) {
        setName("fake-activity");
        setTimestamp(timestamp);
    }

}
