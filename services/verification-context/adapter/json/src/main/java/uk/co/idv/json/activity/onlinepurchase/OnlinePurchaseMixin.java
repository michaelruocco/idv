package uk.co.idv.json.activity.onlinepurchase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "name",
        "timestamp",
        "merchantName",
        "reference",
        "cost"
})
public interface OnlinePurchaseMixin {

    @JsonIgnore
    String getTokenizedCardNumber();

}
