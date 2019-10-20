package uk.co.mruoc.idv.mongo.dao.activity;

import java.time.Instant;

public class ActivityDocumentMother {

    public static ActivityDocument buildDefault() {
        return new DefaultActivityDocument();
    }

    public static OnlinePurchaseDocument buildOnlinePurchase() {
        return new DefaultOnlinePurchaseActivityDocument();
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

    private static class DefaultOnlinePurchaseActivityDocument extends OnlinePurchaseDocument {

        private DefaultOnlinePurchaseActivityDocument() {
            setName("online-purchase");
            setTimestamp(Instant.now().toString());
            setMerchantName("fake-merchant-name");
            setReference("fake-reference");
            setCost(new FakeMonetaryAmountDocument());
        }

    }

}
