package uk.co.idv.domain.entities.activity;

import lombok.Builder;
import lombok.Data;

import javax.money.MonetaryAmount;
import java.time.Instant;

@Builder
@Data
public class OnlinePurchase implements Activity {

    public static final String NAME = "online-purchase";

    private final Instant timestamp;
    private final String merchantName;
    private final String reference;
    private final MonetaryAmount cost;
    //TODO add card number

    @Override
    public String getName() {
        return NAME;
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
