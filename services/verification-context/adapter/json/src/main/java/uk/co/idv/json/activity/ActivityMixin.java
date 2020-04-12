package uk.co.idv.json.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "name",
        "timestamp",
        "merchantName",
        "reference",
        "cost"
})
public interface ActivityMixin {

    @JsonIgnore
    String getTokenizedCardNumber();

}