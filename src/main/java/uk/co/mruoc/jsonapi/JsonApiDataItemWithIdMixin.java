package uk.co.mruoc.jsonapi;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "id",
        "type",
        "attributes"
})
interface JsonApiDataItemWithIdMixin {

    //intentionally blank

}
