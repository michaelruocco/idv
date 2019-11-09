package uk.co.idv.repository.mongo.activity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OnlinePurchaseDocument extends ActivityDocument {

    private String merchantName;
    private String reference;
    private MonetaryAmountDocument cost;

}
