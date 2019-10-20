package uk.co.mruoc.idv.mongo.dao.activity;

import java.time.Instant;

public class FakeActivityDocument extends ActivityDocument {

    public FakeActivityDocument() {
        this(Instant.now().toString());
    }

    public FakeActivityDocument(final String timestamp) {
        setName("fake-activity");
        setTimestamp(timestamp);
    }



}
