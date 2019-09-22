package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "type",
        "attributes"
})
interface JsonApiDataItemMixin {

    //intentionally blank

}
