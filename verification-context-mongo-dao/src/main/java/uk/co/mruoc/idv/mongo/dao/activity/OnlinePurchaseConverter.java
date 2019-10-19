package uk.co.mruoc.idv.mongo.dao.activity;

import lombok.Builder;
import uk.co.mruoc.idv.domain.model.activity.Activity;
import uk.co.mruoc.idv.domain.model.activity.OnlinePurchase;

import java.time.Instant;

@Builder
public class OnlinePurchaseConverter implements ActivityConverter {

    private final MonetaryAmountConverter amountConverter;

    @Override
    public String getSupportedActivityName() {
        return OnlinePurchase.NAME;
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
