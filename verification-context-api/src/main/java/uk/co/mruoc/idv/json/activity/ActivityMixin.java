package uk.co.mruoc.idv.json.activity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "name",
        "timestamp",
        "merchantName",
        "reference",
        "cost"})
public interface ActivityMixin {

    // intentionally blank

}
