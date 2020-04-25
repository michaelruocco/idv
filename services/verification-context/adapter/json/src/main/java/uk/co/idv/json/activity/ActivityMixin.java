package uk.co.idv.json.activity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "name",
        "timestamp",
})
public interface ActivityMixin {

    // intentionally blank

}
