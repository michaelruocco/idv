package uk.co.mruoc.idv.verificationcontext.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "id",
        "channel",
        "providedAlias",
        "identity",
        "activity",
        "sequences",
        "created",
        "expiry"
})
public interface VerificationContextMixin {

    @JsonIgnore
    String getChannelId();

}