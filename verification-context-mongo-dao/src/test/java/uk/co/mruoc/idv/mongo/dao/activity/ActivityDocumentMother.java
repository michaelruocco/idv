package uk.co.mruoc.idv.mongo.dao.activity;

import java.time.Instant;

public class ActivityDocumentMother {

    public static ActivityDocument buildDefault() {
        return new DefaultActivityDocument();
    }

    public static ActivityDocument withName(final String name) {
        final ActivityDocument document = buildDefault();
        document.setName(name);
        return document;
    }

    public static ActivityDocument withTimestamp(final String timestamp) {
        final ActivityDocument document = buildDefault();
        document.setTimestamp(timestamp);
        return document;
    }

    private static class DefaultActivityDocument extends ActivityDocument {

        private DefaultActivityDocument() {
            setName("fake-activity");
            setTimestamp(Instant.now().toString());
        }

    }

}
