package uk.co.mruoc.idv.identity.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

@JsonPropertyOrder({
        "id",
        "aliases"
})
public interface IdentityMixin {

    @JsonProperty("id")
    UUID getIdvIdValue();

}