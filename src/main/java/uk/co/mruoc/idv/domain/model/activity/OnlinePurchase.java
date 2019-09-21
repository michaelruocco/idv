package uk.co.mruoc.idv.domain.model.activity;

import lombok.Builder;

import javax.money.MonetaryAmount;
import java.time.Instant;

@Builder
public class OnlinePurchase implements Activity {

    private final Instant timestamp;
    private final String merchantName;
    private final String reference;
    private final MonetaryAmount cost;

    @Override
    public String getName() {
        return "online-purchase";
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getReference() {
        return reference;
    }

    public MonetaryAmount getCost() {
        return cost;
    }

}
