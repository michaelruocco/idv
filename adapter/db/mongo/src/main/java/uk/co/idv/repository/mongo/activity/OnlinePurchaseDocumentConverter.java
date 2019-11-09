package uk.co.idv.repository.mongo.activity;

import lombok.RequiredArgsConstructor;
import uk.co.idv.domain.entities.activity.Activity;
import uk.co.idv.domain.entities.activity.OnlinePurchase;

import java.time.Instant;

@RequiredArgsConstructor
public class OnlinePurchaseDocumentConverter implements ActivityDocumentConverter {

    private final MonetaryAmountDocumentConverter amountConverter;

    @Override
    public boolean supportsActivity(final String activityName) {
        return OnlinePurchase.NAME.equals(activityName);
    }

    @Override
    public Activity toActivity(final ActivityDocument activityDocument) {
        final OnlinePurchaseDocument document = (OnlinePurchaseDocument) activityDocument;
        return OnlinePurchase.builder()
                .timestamp(Instant.parse(document.getTimestamp()))
                .merchantName(document.getMerchantName())
                .reference(document.getReference())
                .cost(amountConverter.toMonetaryAmount(document.getCost()))
                .build();
    }

    @Override
    public ActivityDocument toDocument(final Activity activity) {
        final OnlinePurchase onlinePurchase = (OnlinePurchase) activity;
        final OnlinePurchaseDocument document = new OnlinePurchaseDocument();
        document.setName(onlinePurchase.getName());
        document.setTimestamp(onlinePurchase.getTimestamp().toString());
        document.setMerchantName(onlinePurchase.getMerchantName());
        document.setReference(onlinePurchase.getReference());
        document.setCost(amountConverter.toDocument(onlinePurchase.getCost()));
        return document;
    }

}
