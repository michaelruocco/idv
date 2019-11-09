package uk.co.mruoc.idv.mongo.dao.activity;

import java.time.Instant;

public class ActivityDocumentMother {

    public static ActivityDocument withTimestamp(final String timestamp) {
        final ActivityDocument document = fake();
        document.setTimestamp(timestamp);
        return document;
    }

    public static ActivityDocument fake() {
        final ActivityDocument document = new ActivityDocument();
        document.setName("fake-activity");
        document.setTimestamp(Instant.now().toString());
        return document;
    }

    public static OnlinePurchaseDocument onlinePurchase() {
        final OnlinePurchaseDocument document = new OnlinePurchaseDocument();
        document.setName("online-purchase");
        document.setTimestamp(Instant.now().toString());
        document.setMerchantName("fake-merchant-name");
        document.setReference("fake-reference");
        document.setCost(MonetaryAmountDocumentMother.build());
        return document;
    }

}
