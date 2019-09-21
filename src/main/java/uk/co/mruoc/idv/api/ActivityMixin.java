package uk.co.mruoc.idv.api;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "name",
        "timestamp",
        "merchantName",
        "reference",
        "cost"})
interface ActivityMixin {

    //intentionally blank

}
