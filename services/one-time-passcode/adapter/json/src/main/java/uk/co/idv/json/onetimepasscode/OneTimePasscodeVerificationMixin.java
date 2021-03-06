package uk.co.idv.json.onetimepasscode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "id",
        "contextId",
        "created",
        "expiry",
        "complete",
        "status",
        "maxDeliveries",
        "maxAttempts"
})
public interface OneTimePasscodeVerificationMixin {

    @JsonIgnore
    int getDeliveriesRemaining();

    @JsonIgnore
    int getAttemptsRemaining();

    @JsonIgnore
    boolean isSuccessful();

}
